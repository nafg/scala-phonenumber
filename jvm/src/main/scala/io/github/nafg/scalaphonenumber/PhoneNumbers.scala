package io.github.nafg.scalaphonenumber

import scala.util.Try

import com.google.i18n.phonenumbers.PhoneNumberUtil.{MatchType, PhoneNumberFormat}


object PhoneNumbers extends PhoneNumberApi {
  override type Underlying = com.google.i18n.phonenumbers.Phonenumber.PhoneNumber

  lazy val PhoneNumberUtil = com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance()

  lazy val matchTypes = Set(MatchType.EXACT_MATCH, MatchType.NSN_MATCH, MatchType.SHORT_NSN_MATCH)

  def matches(underlying: Underlying, string: String) =
    matchTypes contains PhoneNumberUtil.isNumberMatch(underlying, string)

  override def possibleNumber(string: String) =
    PhoneNumberUtil.isPossibleNumber(string, "US")

  override def fromUnderlying(underlying: Underlying) =
    PhoneNumber.raw(PhoneNumberUtil.format(underlying, PhoneNumberFormat.E164))

  override def parseUnderlying(string: String) = Try {
    PhoneNumberUtil.parse(string, "US")
  }

  override def formatNational(underlying: Underlying) =
    PhoneNumberUtil.format(underlying, PhoneNumberFormat.NATIONAL)
}
