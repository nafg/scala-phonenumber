package io.github.nafg.scalaphonenumber

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}


case class PhoneNumber private(raw: String) extends AnyVal

object PhoneNumber {
  def raw(string: String) = PhoneNumber(string)

  implicit lazy val encodePhoneNumber: Encoder[PhoneNumber] = deriveEncoder[PhoneNumber]
  implicit lazy val decodePhoneNumber: Decoder[PhoneNumber] = deriveDecoder[PhoneNumber]
}
