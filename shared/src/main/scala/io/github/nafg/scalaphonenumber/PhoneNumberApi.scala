package io.github.nafg.scalaphonenumber

import scala.util.Try

trait PhoneNumberApi {
  type Underlying

  def possibleNumber(string: String): Boolean
  def fromUnderlying(underlying: Underlying): PhoneNumber
  def parseUnderlying(string: String): Try[Underlying]
  def formatNational(underlying: Underlying): String
  def format(underlying: Underlying, format: PhoneNumber.Format): String

  final def formatNational(phoneNumber: PhoneNumber): String                           =
    parseUnderlying(phoneNumber.raw).fold(_ => phoneNumber.raw, formatNational)
  final def format(phoneNumber: PhoneNumber, numberFormat: PhoneNumber.Format): String =
    parseUnderlying(phoneNumber.raw).fold(
      { e =>
        e.printStackTrace()
        phoneNumber.raw
      },
      format(_, numberFormat)
    )

  final def parse(string: String): Try[PhoneNumber] = parseUnderlying(string).map(fromUnderlying)

  def isValid(underlying: Underlying): Boolean

  final def isValid(string: String): Boolean = parseUnderlying(string).toOption.exists(isValid)
}
