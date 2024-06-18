package io.github.nafg.scalaphonenumber

import scala.util.Try

trait PhoneNumberApi {
  def parse(input: PhoneNumber.Input): Try[PhoneNumber]
  def parseOrRaw(input: PhoneNumber.Input): PhoneNumber

  def countryCallingCode(region: String): Option[Int]
}
