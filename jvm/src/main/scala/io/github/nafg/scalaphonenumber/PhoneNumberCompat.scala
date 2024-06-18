package io.github.nafg.scalaphonenumber

trait PhoneNumberCompat {
  def api: PhoneNumberApi = JvmPhoneNumber.Api
}
