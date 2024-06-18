package io.github.nafg.scalaphonenumber

class PhoneNumberTests extends munit.FunSuite {
  private lazy val number = {
    implicit def country: PhoneNumber.DefaultCountry = PhoneNumber.DefaultCountry.Country("US")
    PhoneNumber.parse("12345678901").get
  }

  implicit def country: PhoneNumber.DefaultCountry = PhoneNumber.DefaultCountry.NoDefault

  test("PhoneNumber.parse") {
    assertEquals(PhoneNumber.parse("+12345678901").get.formatE164, "+12345678901")

    assert(PhoneNumber.parse("000").isFailure)
  }

  test("PhoneNumber.parseOrRaw") {
    val parsedOrRaw = PhoneNumber.parseOrRaw("123")
    assertEquals(parsedOrRaw.input.raw, "123")
    assertEquals(parsedOrRaw.formatNational, "123")
  }

  test("PhoneNumber#formatNational") {
    assertEquals(number.formatNational, "(234) 567-8901")
  }

  test("PhoneNumber#formatInternational") {
    assertEquals(number.formatInternational.replaceAll("[ -]", ""), "+12345678901")
  }

  test("PhoneNumber#format") {
    assertEquals(number.format(PhoneNumber.Format.RFC3966).replaceAll("[ -]", ""), "tel:+12345678901")
  }

  test("PhoneNumber#formatLocal") {
    assertEquals(number.formatLocal(Some(1)), "(234) 567-8901")
    assertEquals(number.formatLocal(Some(972)).replace("-", " "), "+1 234 567 8901")
  }

  test("PhoneNumber#isValid") {
    assert(number.isValid)
  }

  test("PhoneNumber#isPossible") {
    assert(number.isPossible)
  }

  test("PhoneNumber#countryCode") {
    assertEquals(number.countryCallingCode, Some(1))
  }
}
