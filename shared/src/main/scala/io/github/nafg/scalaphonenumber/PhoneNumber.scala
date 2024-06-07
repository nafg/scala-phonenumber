package io.github.nafg.scalaphonenumber

import io.circe.{Decoder, Encoder}

import scala.util.Try

case class PhoneNumber private (raw: String) extends AnyVal {
  def format(format: PhoneNumber.Format)(implicit api: PhoneNumberApi): String  = api.format(this, format)
  def formatNational(implicit api: PhoneNumberApi): String                      = format(PhoneNumber.Format.National)
  def formatInternational(implicit api: PhoneNumberApi): String                 =
    format(PhoneNumber.Format.International)
  def formatE164(implicit api: PhoneNumberApi): String                          = format(PhoneNumber.Format.E164)
  def formatLocal(implicit api: PhoneNumberApi, countryCodes: Set[Int]): String = api.formatLocal(this, countryCodes)

  def isValid(implicit api: PhoneNumberApi): Boolean = api.isValid(raw)
}

object PhoneNumber {
  def raw(string: String): PhoneNumber = PhoneNumber(string)

  def parse(string: String)(implicit api: PhoneNumberApi): Try[PhoneNumber] = api.parse(string)

  implicit lazy val encodePhoneNumber: Encoder[PhoneNumber] = Encoder.encodeString.contramap(_.raw)
  implicit lazy val decodePhoneNumber: Decoder[PhoneNumber] = Decoder.decodeString.map(raw)

  sealed trait Format extends Product with Serializable
  object Format {
    case object National      extends Format
    case object International extends Format
    case object E164          extends Format
    case object RFC3966       extends Format
  }
}
