package io.github.nafg.scalaphonenumber

import com.google.i18n.phonenumbers.PhoneNumberUtil.{MatchType, PhoneNumberFormat}
import com.google.i18n.phonenumbers.{PhoneNumberUtil, Phonenumber}

import scala.util.Try

object JvmPhoneNumber {
  object Api extends PhoneNumberApi {
    lazy val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()

    def possibleNumber(input: PhoneNumber.Input): Boolean =
      phoneNumberUtil.isPossibleNumber(input.raw, input.defaultCountry.idOrNull)

    override def parse(input: PhoneNumber.Input): Try[JvmPhoneNumber] =
      Try(phoneNumberUtil.parse(input.raw, input.defaultCountry.idOrNull))
        .map(underlying => new JvmPhoneNumber(input, Some(underlying)))

    override def parseOrRaw(input: PhoneNumber.Input): JvmPhoneNumber =
      parse(input).getOrElse(new JvmPhoneNumber(input, None))

    def format(underlying: Phonenumber.PhoneNumber, format: PhoneNumber.Format): String =
      phoneNumberUtil.format(underlying, convertFormat(format))

    private def convertFormat(format: PhoneNumber.Format) =
      format match {
        case PhoneNumber.Format.National      => PhoneNumberFormat.NATIONAL
        case PhoneNumber.Format.International => PhoneNumberFormat.INTERNATIONAL
        case PhoneNumber.Format.E164          => PhoneNumberFormat.E164
        case PhoneNumber.Format.RFC3966       => PhoneNumberFormat.RFC3966
      }

    def isValid(underlying: Phonenumber.PhoneNumber): Boolean = phoneNumberUtil.isValidNumber(underlying)

    override def countryCallingCode(region: String): Option[Int] =
      Option(phoneNumberUtil.getCountryCodeForRegion(region)).filter(_ > 0)
  }
}

class JvmPhoneNumber(override val input: PhoneNumber.Input, underlying: Option[Phonenumber.PhoneNumber])
    extends PhoneNumber {
  override protected def api: PhoneNumberApi = JvmPhoneNumber.Api

  override def countryCallingCode: Option[Int] =
    underlying.flatMap { p =>
      Option.when(p.hasCountryCode)(p.getCountryCode)
    }

  override def format(format: PhoneNumber.Format): String =
    underlying.fold(input.raw)(JvmPhoneNumber.Api.format(_, format))

  override def isPossible: Boolean = JvmPhoneNumber.Api.possibleNumber(input)

  override def isValid: Boolean = underlying.exists(JvmPhoneNumber.Api.isValid)
}
