package io.github.nafg.scalaphonenumber
import io.github.nafg.scalaphonenumber.Implicits.phoneNumberApi

class PhoneNumberTests extends munit.FunSuite {
  private val number = PhoneNumbers.parse("12345678901", Some("US")).get

  test("PhoneNumber#formatNational") {
    assertEquals(number.formatNational, "(234) 567-8901")
  }

  test("PhoneNumber#formatInternational") {
    assertEquals(number.formatInternational, "+1 234 567 8901")
  }

  test("PhoneNumber#formatE164") {
    assertEquals(number.formatE164, "+12345678901")
  }

  test("PhoneNumber#format") {
    assertEquals(number.format(PhoneNumber.Format.RFC3966), "tel:+12345678901")
  }

  test("PhoneNumber#isValid") {
    assertEquals(number.isValid, true)
  }
}
