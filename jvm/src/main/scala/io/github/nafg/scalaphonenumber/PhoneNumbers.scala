package io.github.nafg.scalaphonenumber

import com.google.i18n.phonenumbers
import com.google.i18n.phonenumbers.PhoneNumberUtil.{MatchType, PhoneNumberFormat}
import com.google.i18n.phonenumbers.{PhoneNumberUtil, Phonenumber}

import scala.util.Try

object PhoneNumbers extends PhoneNumberApi {
  override type Underlying = com.google.i18n.phonenumbers.Phonenumber.PhoneNumber

  lazy val PhoneNumberUtil: PhoneNumberUtil = phonenumbers.PhoneNumberUtil.getInstance()

  private lazy val matchTypes: Set[MatchType] =
    Set(MatchType.EXACT_MATCH, MatchType.NSN_MATCH, MatchType.SHORT_NSN_MATCH)

  def matches(underlying: Underlying, string: String): Boolean =
    matchTypes contains PhoneNumberUtil.isNumberMatch(underlying, string)

  override def possibleNumber(string: String, defaultCountry: Option[String] = None): Boolean =
    PhoneNumberUtil.isPossibleNumber(string, defaultCountry.orNull)

  override def fromUnderlying(underlying: Underlying): PhoneNumber =
    PhoneNumber.raw(PhoneNumberUtil.format(underlying, PhoneNumberFormat.E164))

  override def parseUnderlying(string: String, defaultCountry: Option[String] = None): Try[Underlying] = Try {
    PhoneNumberUtil.parse(string, defaultCountry.orNull)
  }

  override def formatNational(underlying: Underlying): String =
    PhoneNumberUtil.format(underlying, PhoneNumberFormat.NATIONAL)

  override def format(underlying: Underlying, format: PhoneNumber.Format): String =
    PhoneNumberUtil.format(
      underlying,
      format match {
        case PhoneNumber.Format.National      => PhoneNumberFormat.NATIONAL
        case PhoneNumber.Format.International => PhoneNumberFormat.INTERNATIONAL
        case PhoneNumber.Format.E164          => PhoneNumberFormat.E164
        case PhoneNumber.Format.RFC3966       => PhoneNumberFormat.RFC3966
      }
    )

  override def isValid(underlying: Underlying): Boolean = PhoneNumberUtil.isValidNumber(underlying)
}
