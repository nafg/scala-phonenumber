package io.github.nafg.scalaphonenumber

import scala.util.Try

import io.github.nafg.scalaphonenumber.facade.LibphonenumberJs


object PhoneNumbers extends PhoneNumberApi {
  override type Underlying = LibphonenumberJs.PhoneNumber

  override def possibleNumber(string: String) =
    parseUnderlying(string).fold(_ => false, _.isPossible())

  override def fromUnderlying(underlying: Underlying) =
    PhoneNumber.raw(underlying.number)

  override def parseUnderlying(string: String) = Try {
    LibphonenumberJs.parsePhoneNumber(string, "US")
  }

  override def formatNational(underlying: Underlying) =
    underlying.formatNational()

  def matches(a: PhoneNumber, b: PhoneNumber) =
    formatNational(a) == formatNational(b)

  override def isValid(underlying: Underlying) = underlying.isValid()
}
