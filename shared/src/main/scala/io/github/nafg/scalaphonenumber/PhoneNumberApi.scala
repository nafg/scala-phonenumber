package io.github.nafg.scalaphonenumber

import scala.util.Try

trait PhoneNumberApi {
  type Underlying

  def possibleNumber(string: String, defaultCountry: Option[String] = None): Boolean
  def fromUnderlying(underlying: Underlying): PhoneNumber
  def parseUnderlying(string: String, defaultCountry: Option[String] = None): Try[Underlying]
  final def toUnderlying(phoneNumber: PhoneNumber): Try[Underlying] = parseUnderlying(phoneNumber.raw)

  def countryCode(underlying: Underlying): Option[Int]

  def formatNational(underlying: Underlying): String
  def format(underlying: Underlying, format: PhoneNumber.Format): String
  final def formatNational(phoneNumber: PhoneNumber): String                           =
    toUnderlying(phoneNumber).fold(_ => phoneNumber.raw, formatNational)
  final def format(phoneNumber: PhoneNumber, numberFormat: PhoneNumber.Format): String =
    toUnderlying(phoneNumber).fold(_ => phoneNumber.raw, format(_, numberFormat))

  /** Like formatNational if countryCode is in countryCodes, otherwise formatInternational
    *
    * @param phoneNumber
    *   The phone number to format
    * @param countryCodes
    *   The country codes which should be formatted nationally as opposed to internationally. Pass the empty set to
    *   always formatNational.
    * @return
    */
  def formatLocal(phoneNumber: PhoneNumber, countryCodes: Set[Int]): String =
    toUnderlying(phoneNumber)
      .map { underlying =>
        format(
          underlying,
          if (countryCode(underlying).forall(countryCodes))
            PhoneNumber.Format.National
          else
            PhoneNumber.Format.International
        )
      }
      .getOrElse(phoneNumber.raw)

  final def parse(string: String, defaultCountry: Option[String] = None): Try[PhoneNumber] =
    parseUnderlying(string, defaultCountry).map(fromUnderlying)

  def isValid(underlying: Underlying): Boolean

  final def isValid(string: String): Boolean = parseUnderlying(string).toOption.exists(isValid)
}
