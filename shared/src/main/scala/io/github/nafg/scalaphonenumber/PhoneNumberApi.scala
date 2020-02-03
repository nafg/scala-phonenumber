package io.github.nafg.scalaphonenumber

import scala.util.Try


trait PhoneNumberApi {
  type Underlying

  def possibleNumber(string: String): Boolean
  def fromUnderlying(underlying: Underlying): PhoneNumber
  def parseUnderlying(string: String): Try[Underlying]
  def formatNational(underlying: Underlying): String

  final def formatNational(phoneNumber: PhoneNumber): String =
    parseUnderlying(phoneNumber.raw).fold(_ => phoneNumber.raw, formatNational)

  final def parse(string: String) = parseUnderlying(string).map(fromUnderlying)

  def isValid(underlying: Underlying): Boolean

  final def isValid(string: String): Boolean = parseUnderlying(string).toOption.exists(isValid)
}
