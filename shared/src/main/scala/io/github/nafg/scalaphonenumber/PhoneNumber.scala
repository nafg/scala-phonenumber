package io.github.nafg.scalaphonenumber

import io.circe.generic.semiauto.deriveCodec
import io.circe.{Codec, Decoder, Encoder}

import scala.util.Try

trait PhoneNumber {
  def input: PhoneNumber.Input

  final def raw: String = input.raw

  def countryCallingCode: Option[Int]

  def format(format: PhoneNumber.Format): String

  final def formatNational: String      = format(PhoneNumber.Format.National)
  final def formatInternational: String = format(PhoneNumber.Format.International)
  lazy val formatE164: String           = format(PhoneNumber.Format.E164)

  final def formatLocal(localCountryCallingCode: Option[Int]): String =
    if (countryCallingCode == localCountryCallingCode)
      formatNational
    else
      formatInternational

  protected def api: PhoneNumberApi

  final def formatLocal(implicit defaultCountry: PhoneNumber.DefaultCountry): String =
    defaultCountry match {
      case PhoneNumber.DefaultCountry.NoDefault   => formatInternational
      case PhoneNumber.DefaultCountry.Country(id) => formatLocal(api.countryCallingCode(id))
    }

  def isPossible: Boolean
  def isValid: Boolean

  override def equals(obj: Any): Boolean = obj match {
    case that: PhoneNumber => this.formatE164 == that.formatE164
    case _                 => false
  }

  override def hashCode(): Int = formatE164.hashCode

  override def toString: String = formatE164
}
object PhoneNumber extends PhoneNumberCompat {
  sealed trait DefaultCountry {
    def idOrNull: String =
      this match {
        case DefaultCountry.Country(id) => id
        case DefaultCountry.NoDefault   => null
      }
  }
  object DefaultCountry       {
    case class Country(id: String) extends DefaultCountry
    case object NoDefault          extends DefaultCountry

    val US = Country("US")
    val CA = Country("CA")

    implicit lazy val encodeDefaultCountry: Encoder[DefaultCountry] =
      Encoder.encodeOption[String].contramap {
        case Country(id) => Some(id)
        case NoDefault   => None
      }
    implicit lazy val decodeDefaultCountry: Decoder[DefaultCountry] =
      Decoder.decodeOption[String].map {
        case Some(id) => Country(id)
        case None     => NoDefault
      }
  }

  case class Input(raw: String, defaultCountry: DefaultCountry)
  object Input {
    implicit lazy val codecPhoneNumberInput: Codec[Input] = deriveCodec[Input]
  }

  def parseOrRaw(string: String)(implicit defaultCountry: DefaultCountry): PhoneNumber =
    api.parseOrRaw(Input(string, defaultCountry))

  def parse(string: String)(implicit defaultCountry: DefaultCountry): Try[PhoneNumber] =
    api.parse(Input(string, defaultCountry))

  def parseInternationalOrRaw(string: String): PhoneNumber = parseOrRaw(string)(DefaultCountry.NoDefault)
  def parseInternational(string: String): Try[PhoneNumber] = parse(string)(DefaultCountry.NoDefault)

  def empty: PhoneNumber = parseInternationalOrRaw("")

  object Implicits {
    implicit lazy val codecPhoneNumberAsInput: Codec[PhoneNumber] =
      Codec.from(Input.codecPhoneNumberInput.map(api.parseOrRaw(_)), Input.codecPhoneNumberInput.contramap(_.input))

    implicit lazy val codecPhoneNumberAsE164String: Codec[PhoneNumber] =
      Codec.from(Decoder[String].map(parseInternationalOrRaw), Encoder[String].contramap(_.formatE164))
  }

  sealed trait Format extends Product with Serializable
  object Format {
    case object National      extends Format
    case object International extends Format
    case object E164          extends Format
    case object RFC3966       extends Format
  }
}
