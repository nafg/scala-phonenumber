package io.github.nafg.scalaphonenumber

import io.github.nafg.scalaphonenumber.PhoneNumber.DefaultCountry
import io.github.nafg.scalaphonenumber.facade.libphonenumberJs.{mod, typesMod}

import scala.util.Try

object JsPhoneNumber    {
  object Api extends PhoneNumberApi {
    override def parse(input: PhoneNumber.Input): Try[JsPhoneNumber] =
      Try {
        input.defaultCountry match {
          case DefaultCountry.NoDefault   => mod.parsePhoneNumberWithError(input.raw)
          case DefaultCountry.Country(id) =>
            mod.parsePhoneNumberWithError(input.raw, id.asInstanceOf[typesMod.CountryCode])
        }
      }
        .map(phoneNumber => new JsPhoneNumber(input, Some(phoneNumber)))

    override def parseOrRaw(input: PhoneNumber.Input): JsPhoneNumber =
      parse(input: PhoneNumber.Input).getOrElse(new JsPhoneNumber(input, None))

    def convertFormat(format: PhoneNumber.Format): typesMod.NumberFormat =
      format match {
        case PhoneNumber.Format.National      => typesMod.NumberFormat.NATIONAL
        case PhoneNumber.Format.International => typesMod.NumberFormat.INTERNATIONAL
        case PhoneNumber.Format.E164          => typesMod.NumberFormat.EDot164
        case PhoneNumber.Format.RFC3966       => typesMod.NumberFormat.RFC3966
      }

    override def countryCallingCode(region: String): Option[Int] =
      Option(mod.getCountryCallingCode(region.asInstanceOf[typesMod.CountryCode])).flatMap(_.toIntOption)
  }
}
class JsPhoneNumber(override val input: PhoneNumber.Input, underlying: Option[typesMod.PhoneNumber])
    extends PhoneNumber {
  override protected def api: PhoneNumberApi = JsPhoneNumber.Api

  override def countryCallingCode: Option[Int] =
    underlying.flatMap(_.countryCallingCode.toIntOption)

  override def format(fmt: PhoneNumber.Format): String =
    underlying.fold(input.raw) { phoneNumber =>
      val numberFormat = JsPhoneNumber.Api.convertFormat(fmt)
      phoneNumber.format(numberFormat)
    }

  override def isPossible: Boolean = underlying.exists(_.isPossible())
  override def isValid: Boolean    = underlying.exists(_.isValid())
}
