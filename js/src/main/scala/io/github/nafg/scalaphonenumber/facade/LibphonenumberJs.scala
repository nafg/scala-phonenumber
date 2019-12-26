package io.github.nafg.scalaphonenumber.facade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|


@js.native
@JSImport("libphonenumber-js", JSImport.Namespace)
object LibphonenumberJs extends js.Object {
  /**
   * '001' | 'AC' | 'AD' | 'AE' | 'AF' | 'AG' | 'AI' | 'AL' | 'AM' | 'AO' | 'AR' | 'AS' | 'AT' | 'AU' | 'AW' | 'AX' |
   * 'AZ' | 'BA' | 'BB' | 'BD' | 'BE' | 'BF' | 'BG' | 'BH' | 'BI' | 'BJ' | 'BL' | 'BM' | 'BN' | 'BO' | 'BQ' | 'BR' |
   * 'BS' | 'BT' | 'BW' | 'BY' | 'BZ' | 'CA' | 'CC' | 'CD' | 'CF' | 'CG' | 'CH' | 'CI' | 'CK' | 'CL' | 'CM' | 'CN' |
   * 'CO' | 'CR' | 'CU' | 'CV' | 'CW' | 'CX' | 'CY' | 'CZ' | 'DE' | 'DJ' | 'DK' | 'DM' | 'DO' | 'DZ' | 'EC' | 'EE' |
   * 'EG' | 'EH' | 'ER' | 'ES' | 'ET' | 'FI' | 'FJ' | 'FK' | 'FM' | 'FO' | 'FR' | 'GA' | 'GB' | 'GD' | 'GE' | 'GF' |
   * 'GG' | 'GH' | 'GI' | 'GL' | 'GM' | 'GN' | 'GP' | 'GQ' | 'GR' | 'GT' | 'GU' | 'GW' | 'GY' | 'HK' | 'HN' | 'HR' |
   * 'HT' | 'HU' | 'ID' | 'IE' | 'IL' | 'IM' | 'IN' | 'IO' | 'IQ' | 'IR' | 'IS' | 'IT' | 'JE' | 'JM' | 'JO' | 'JP' |
   * 'KE' | 'KG' | 'KH' | 'KI' | 'KM' | 'KN' | 'KP' | 'KR' | 'KW' | 'KY' | 'KZ' | 'LA' | 'LB' | 'LC' | 'LI' | 'LK' |
   * 'LR' | 'LS' | 'LT' | 'LU' | 'LV' | 'LY' | 'MA' | 'MC' | 'MD' | 'ME' | 'MF' | 'MG' | 'MH' | 'MK' | 'ML' | 'MM' |
   * 'MN' | 'MO' | 'MP' | 'MQ' | 'MR' | 'MS' | 'MT' | 'MU' | 'MV' | 'MW' | 'MX' | 'MY' | 'MZ' | 'NA' | 'NC' | 'NE' |
   * 'NF' | 'NG' | 'NI' | 'NL' | 'NO' | 'NP' | 'NR' | 'NU' | 'NZ' | 'OM' | 'PA' | 'PE' | 'PF' | 'PG' | 'PH' | 'PK' |
   * 'PL' | 'PM' | 'PR' | 'PS' | 'PT' | 'PW' | 'PY' | 'QA' | 'RE' | 'RO' | 'RS' | 'RU' | 'RW' | 'SA' | 'SB' | 'SC' |
   * 'SD' | 'SE' | 'SG' | 'SH' | 'SI' | 'SJ' | 'SK' | 'SL' | 'SM' | 'SN' | 'SO' | 'SR' | 'SS' | 'ST' | 'SV' | 'SX' |
   * 'SY' | 'SZ' | 'TA' | 'TC' | 'TD' | 'TG' | 'TH' | 'TJ' | 'TK' | 'TL' | 'TM' | 'TN' | 'TO' | 'TR' | 'TT' | 'TV' |
   * 'TW' | 'TZ' | 'UA' | 'UG' | 'US' | 'UY' | 'UZ' | 'VA' | 'VC' | 'VE' | 'VG' | 'VI' | 'VN' | 'VU' | 'WF' | 'WS' |
   * 'XK' | 'YE' | 'YT' | 'ZA' | 'ZM' | 'ZW'
   */
  type CountryCode = String

  type CountryCallingCodes = js.Dictionary[js.Array[CountryCode]]
  type Countries = js.Dictionary[js.Array[js.Any]]

  @js.native
  trait Metadata extends js.Object {
    def country_calling_codes: CountryCallingCodes
    def countries: Countries
  }

  /**
   * 'NATIONAL' | 'National' | 'INTERNATIONAL' | 'International' | 'E.164' | 'RFC3966' | 'IDD'
   */
  type NumberFormat = String

  /**
   * 'PREMIUM_RATE' | 'TOLL_FREE' | 'SHARED_COST' | 'VOIP' | 'PERSONAL_NUMBER' | 'PAGER' | 'UAN' | 'VOICEMAIL' |
   * 'FIXED_LINE_OR_MOBILE' | 'FIXED_LINE' | 'MOBILE'
   */
  type NumberType = js.UndefOr[String]

  type E164Number = String
  type NationalNumber = String
  type Extension = String
  type CarrierCode = String
  type CountryCallingCode = String

  type FormatExtension = (String, Extension, Metadata) => String

  @js.native
  trait FormatNumberOptionsWithoutIDD extends js.Object {
    def v2: js.UndefOr[Boolean]
    def formatExtension: js.UndefOr[FormatExtension]
  }

  @js.native
  trait FormatNumberOptions extends FormatNumberOptionsWithoutIDD {
    def fromCountry: js.UndefOr[CountryCode]
    def humanReadable: js.UndefOr[Boolean]
  }

  @js.native class ParseError extends js.Object {
    def message: String = js.native
  }

  @js.native
  class PhoneNumber(countryCallingCodeOrCountry: CountryCallingCode | CountryCode,
                    val nationalNumber: NationalNumber,
                    metadata: Metadata) extends js.Object {
    def countryCallingCode: CountryCallingCode = js.native
    def country: js.UndefOr[CountryCode] = js.native
    def number: E164Number = js.native
    def carrierCode: js.UndefOr[CarrierCode] = js.native
    def ext: js.UndefOr[Extension] = js.native
    def isPossible(): Boolean = js.native
    def isValid(): Boolean = js.native
    def getType(): NumberType = js.native
    def format(format: NumberFormat, options: js.UndefOr[FormatNumberOptions] = js.undefined): String = js.native
    def formatNational(options: js.UndefOr[FormatNumberOptionsWithoutIDD] = js.undefined): String = js.native
    def formatInternational(options: js.UndefOr[FormatNumberOptionsWithoutIDD] = js.undefined): String = js.native
    def getURI(options: js.UndefOr[FormatNumberOptionsWithoutIDD] = js.undefined): String = js.native
  }

  def parsePhoneNumber(number: String, countryName: String): PhoneNumber = js.native
  def parsePhoneNumberFromString(number: String, countryName: String): js.UndefOr[PhoneNumber] = js.native
}
