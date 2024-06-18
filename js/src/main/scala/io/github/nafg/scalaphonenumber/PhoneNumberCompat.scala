package io.github.nafg.scalaphonenumber

trait PhoneNumberCompat {
  implicit def api: PhoneNumberApi = JsPhoneNumber.Api
}
