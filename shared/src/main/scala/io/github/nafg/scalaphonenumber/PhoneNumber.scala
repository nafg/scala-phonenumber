package io.github.nafg.scalaphonenumber

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}


case class PhoneNumber private(raw: String) extends AnyVal {
  def formatNational(implicit api: PhoneNumberApi): String = api.formatNational(this)
  def isValid(implicit api: PhoneNumberApi): Boolean = api.isValid(raw)
}

object PhoneNumber {
  def raw(string: String) = PhoneNumber(string)

  def parse(string: String)(implicit api: PhoneNumberApi) = api.parse(string)

  implicit lazy val encodePhoneNumber: Encoder[PhoneNumber] = deriveEncoder[PhoneNumber]
  implicit lazy val decodePhoneNumber: Decoder[PhoneNumber] = deriveDecoder[PhoneNumber]
}
