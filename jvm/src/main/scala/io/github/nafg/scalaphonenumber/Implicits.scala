package io.github.nafg.scalaphonenumber

object Implicits {
  implicit def phoneNumberApi: PhoneNumberApi = PhoneNumbers
}
