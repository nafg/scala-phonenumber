package io.github.nafg.scalaphonenumber

import io.github.nafg.scalaphonenumber.PhoneNumber.Format

import scala.util.Try
import io.github.nafg.scalaphonenumber.facade.LibphonenumberJs

object PhoneNumbers extends PhoneNumberApi {
  override type Underlying = LibphonenumberJs.PhoneNumber

  override def possibleNumber(string: String): Boolean =
    parseUnderlying(string).fold(_ => false, _.isPossible())

  override def fromUnderlying(underlying: Underlying): PhoneNumber =
    PhoneNumber.raw(underlying.number)

  private val country = "US" // TODO

  override def parseUnderlying(string: String): Try[Underlying] = Try {
    LibphonenumberJs.parsePhoneNumber(string, country)
  }

  override def formatNational(underlying: Underlying): String =
    underlying.formatNational()

  override def format(underlying: Underlying, format: PhoneNumber.Format): String =
    underlying.format(format match {
      case PhoneNumber.Format.National      => "NATIONAL"
      case PhoneNumber.Format.International => "INTERNATIONAL"
      case PhoneNumber.Format.E164          => "E.164"
      case PhoneNumber.Format.RFC3966       => "RFC3966"
    })

  def matches(a: PhoneNumber, b: PhoneNumber): Boolean =
    format(a, PhoneNumber.Format.E164) == format(b, PhoneNumber.Format.E164)

  override def isValid(underlying: Underlying): Boolean = underlying.isValid()
}
