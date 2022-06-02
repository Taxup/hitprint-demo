package kz.app.core.domain

import java.lang.IllegalArgumentException

/**
 * Currency model
 */

data class Currency(
    var currencyCode: String = "",
    var currencySymbol: String = ""
) {

    //TODO change constructor signature
    constructor(currency: String) : this(
        try {
            java.util.Currency.getInstance(currency)
        } catch (e: IllegalArgumentException) {
            java.util.Currency.getInstance("KZT")
        }
    )

    protected constructor(currency: java.util.Currency) : this(
        currency.currencyCode,
        buildSymbol(currency)
    )

    companion object {
        private val CURRENCY_SYMBOLS_LOCALE_INDEPENDENT = hashMapOf(
            Pair("USD", "$"),
            Pair("KZT", "₸"),
            Pair("EUR", "€"),
            Pair("GBP", "£"),
            Pair("CHF", "₣"),
            Pair("RUB", "₽"),
            Pair("JPY", "¥"),
            Pair("AMD", "֏"),
            Pair("AFN", "؋"),
            Pair("BDT", "৳"),
            Pair("THB", "฿"),
            Pair("KHR", "៛"),
            Pair("CRC", "₡"),
            Pair("NGN", "₦"),
            Pair("KRW", "₩"),
            Pair("ILS", "₪"),
            Pair("VND", "₫"),
            Pair("LAK", "₭"),
            Pair("MNT", "₮"),
            Pair("PHP", "₱"),
            Pair("PYG", "₲"),
            Pair("UAH", "₴"),
            Pair("GHS", "₵"),
            Pair("INR", "₹"),
            Pair("TRY", "₺"),
            Pair("IDR", "₨"),
            Pair("QAR", "﷼")
        )

        val KZT = Currency("KZT")
        val USD = Currency("USD")
        val RUB = Currency("RUB")
        val EUR = Currency("EUR")
        val CHF = Currency("CHF")
        val GBP = Currency("GBP")
        val JPY = Currency("JPY")
        val AMD = Currency("AMD")
        val AFN = Currency("AFN")
        val BDT = Currency("BDT")
        val THB = Currency("THB")
        val KHR = Currency("KHR")
        val CRC = Currency("CRC")
        val NGN = Currency("NGN")
        val KRW = Currency("KRW")
        val ILS = Currency("ILS")
        val VND = Currency("VND")
        val LAK = Currency("LAK")
        val MNT = Currency("MNT")
        val PHP = Currency("PHP")
        val PYG = Currency("PYG")
        val UAH = Currency("UAH")
        val GHS = Currency("GHS")
        val INR = Currency("INR")
        val TRY = Currency("TRY")
        val IDR = Currency("IDR")
        val QAR = Currency("QAR")
        val AED = Currency("AED")
        val ALL = Currency("ALL")
        val ANG = Currency("ANG")
        val AOA = Currency("AOA")
        val ARS = Currency("ARS")
        val AUD = Currency("AUD")
        val AZN = Currency("AZN")
        val BAM = Currency("BAM")
        val BBD = Currency("BBD")
        val BGN = Currency("BGN")
        val BHD = Currency("BHD")
        val BIF = Currency("BIF")
        val BMD = Currency("BMD")
        val BND = Currency("BND")
        val BOB = Currency("BOB")
        val BOV = Currency("BOV")
        val BRL = Currency("BRL")
        val BSD = Currency("BSD")
        val BTN = Currency("BTN")
        val BWP = Currency("BWP")
        val BYN = Currency("BYN")
        val BZD = Currency("BZD")
        val CAD = Currency("CAD")
        val CDF = Currency("CDF")
        val CHE = Currency("CHE")
        val CHW = Currency("CHW")
        val CLF = Currency("CLF")
        val CLP = Currency("CLP")
        val CNY = Currency("CNY")
        val COP = Currency("COP")
        val COU = Currency("COU")
        val CUC = Currency("CUC")
        val CUP = Currency("CUP")
        val CVE = Currency("CVE")
        val CZK = Currency("CZK")
        val DJF = Currency("DJF")
        val DKK = Currency("DKK")
        val DOP = Currency("DOP")
        val DZD = Currency("DZD")
        val EGP = Currency("EGP")
        val ERN = Currency("ERN")
        val ETB = Currency("ETB")
        val FJD = Currency("FJD")
        val FKP = Currency("FKP")
        val AWG = Currency("AWG")
        val GEL = Currency("GEL")
        val GGP = Currency("GGP")
        val GIP = Currency("GIP")
        val GMD = Currency("GMD")
        val GNF = Currency("GNF")
        val GTQ = Currency("GTQ")
        val GYD = Currency("GYD")
        val HKD = Currency("HKD")
        val HNL = Currency("HNL")
        val HRK = Currency("HRK")
        val HTG = Currency("HTG")
        val HUF = Currency("HUF")
        val IMP = Currency("IMP")
        val IQD = Currency("IQD")
        val IRR = Currency("IRR")
        val ISK = Currency("ISK")
        val JEP = Currency("JEP")
        val JMD = Currency("JMD")
        val JOD = Currency("JOD")
        val KES = Currency("KES")
        val KGS = Currency("KGS")
        val KMF = Currency("KMF")
        val KPW = Currency("KPW")
        val KWD = Currency("KWD")
        val KYD = Currency("KYD")
        val LBP = Currency("LBP")
        val LKR = Currency("LKR")
        val LRD = Currency("LRD")
        val LSL = Currency("LSL")
        val LTL = Currency("LTL")
        val LYD = Currency("LYD")
        val MAD = Currency("MAD")
        val MDL = Currency("MDL")
        val MGA = Currency("MGA")
        val MKD = Currency("MKD")
        val MMK = Currency("MMK")
        val MOP = Currency("MOP")
        val MRU = Currency("MRU")
        val MUR = Currency("MUR")
        val MVR = Currency("MVR")
        val MWK = Currency("MWK")
        val MXN = Currency("MXN")
        val MXV = Currency("MXV")
        val MYR = Currency("MYR")
        val MZN = Currency("MZN")
        val NAD = Currency("NAD")
        val NIO = Currency("NIO")
        val NOK = Currency("NOK")
        val NPR = Currency("NPR")
        val NZD = Currency("NZD")
        val OMR = Currency("OMR")
        val PAB = Currency("PAB")
        val PEN = Currency("PEN")
        val PGK = Currency("PGK")
        val PKR = Currency("PKR")
        val PLN = Currency("PLN")
        val RON = Currency("RON")
        val RSD = Currency("RSD")
        val RWF = Currency("RWF")
        val SAR = Currency("SAR")
        val SBD = Currency("SBD")
        val SCR = Currency("SCR")
        val SDG = Currency("SDG")
        val SEK = Currency("SEK")
        val SGD = Currency("SGD")
        val SHP = Currency("SHP")
        val SLL = Currency("SLL")
        val SOS = Currency("SOS")
        val SRD = Currency("SRD")
        val SSP = Currency("SSP")
        val STN = Currency("STN")
        val SVC = Currency("SVC")
        val SYP = Currency("SYP")
        val SZL = Currency("SZL")
        val TJS = Currency("TJS")
        val TMT = Currency("TMT")
        val TND = Currency("TND")
        val TOP = Currency("TOP")
        val TTD = Currency("TTD")
        val TWD = Currency("TWD")
        val TZS = Currency("TZS")
        val UGX = Currency("UGX")
        val UYI = Currency("UYI")
        val UYU = Currency("UYU")
        val UZS = Currency("UZS")
        val VEF = Currency("VEF")
        val VUV = Currency("VUV")
        val WST = Currency("WST")
        val XAF = Currency("XAF")
        val XCD = Currency("XCD")
        val XDR = Currency("XDR")
        val XOF = Currency("XOF")
        val XPF = Currency("XPF")
        val YER = Currency("YER")
        val ZAR = Currency("ZAR")
        val ZWL = Currency("ZWL")

        fun create(code: String? = null): Currency {
            if (code == null || code.isEmpty()) return KZT

            when (code) {
                "KZT" -> return KZT
                "USD" -> return USD
                "RUB" -> return RUB
                "EUR" -> return EUR
                "CHF" -> return CHF
                "GBP" -> return GBP
                "JPY" -> return JPY
                "AMD" -> return AMD
                "AFN" -> return AFN
                "BDT" -> return BDT
                "THB" -> return THB
                "KHR" -> return KHR
                "CRC" -> return CRC
                "NGN" -> return NGN
                "KRW" -> return KRW
                "ILS" -> return ILS
                "VND" -> return VND
                "LAK" -> return LAK
                "MNT" -> return MNT
                "PHP" -> return PHP
                "PYG" -> return PYG
                "UAH" -> return UAH
                "GHS" -> return GHS
                "INR" -> return INR
                "TRY" -> return TRY
                "IDR" -> return IDR
                "QAR" -> return QAR
                "AED" -> return AED
                "ALL" -> return ALL
                "ANG" -> return ANG
                "AOA" -> return AOA
                "ARS" -> return ARS
                "AUD" -> return AUD
                "AZN" -> return AZN
                "BAM" -> return BAM
                "BBD" -> return BBD
                "BGN" -> return BGN
                "BHD" -> return BHD
                "BIF" -> return BIF
                "BMD" -> return BMD
                "BND" -> return BND
                "BOB" -> return BOB
                "BOV" -> return BOV
                "BRL" -> return BRL
                "BSD" -> return BSD
                "BTN" -> return BTN
                "BWP" -> return BWP
                "BYN" -> return BYN
                "BZD" -> return BZD
                "CAD" -> return CAD
                "CDF" -> return CDF
                "CHE" -> return CHE
                "CHW" -> return CHW
                "CLF" -> return CLF
                "CLP" -> return CLP
                "CNY" -> return CNY
                "COP" -> return COP
                "COU" -> return COU
                "CUC" -> return CUC
                "CUP" -> return CUP
                "CVE" -> return CVE
                "CZK" -> return CZK
                "DJF" -> return DJF
                "DKK" -> return DKK
                "DOP" -> return DOP
                "DZD" -> return DZD
                "EGP" -> return EGP
                "ERN" -> return ERN
                "ETB" -> return ETB
                "FJD" -> return FJD
                "FKP" -> return FKP
                "AWG" -> return AWG
                "GEL" -> return GEL
                "GGP" -> return GGP
                "GIP" -> return GIP
                "GMD" -> return GMD
                "GNF" -> return GNF
                "GTQ" -> return GTQ
                "GYD" -> return GYD
                "HKD" -> return HKD
                "HNL" -> return HNL
                "HRK" -> return HRK
                "HTG" -> return HTG
                "HUF" -> return HUF
                "IMP" -> return IMP
                "IQD" -> return IQD
                "IRR" -> return IRR
                "ISK" -> return ISK
                "JEP" -> return JEP
                "JMD" -> return JMD
                "JOD" -> return JOD
                "KES" -> return KES
                "KGS" -> return KGS
                "KMF" -> return KMF
                "KPW" -> return KPW
                "KWD" -> return KWD
                "KYD" -> return KYD
                "LBP" -> return LBP
                "LKR" -> return LKR
                "LRD" -> return LRD
                "LSL" -> return LSL
                "LTL" -> return LTL
                "LYD" -> return LYD
                "MAD" -> return MAD
                "MDL" -> return MDL
                "MGA" -> return MGA
                "MKD" -> return MKD
                "MMK" -> return MMK
                "MOP" -> return MOP
                "MRU" -> return MRU
                "MUR" -> return MUR
                "MVR" -> return MVR
                "MWK" -> return MWK
                "MXN" -> return MXN
                "MXV" -> return MXV
                "MYR" -> return MYR
                "MZN" -> return MZN
                "NAD" -> return NAD
                "NIO" -> return NIO
                "NOK" -> return NOK
                "NPR" -> return NPR
                "NZD" -> return NZD
                "OMR" -> return OMR
                "PAB" -> return PAB
                "PEN" -> return PEN
                "PGK" -> return PGK
                "PKR" -> return PKR
                "PLN" -> return PLN
                "RON" -> return RON
                "RSD" -> return RSD
                "RWF" -> return RWF
                "SAR" -> return SAR
                "SBD" -> return SBD
                "SCR" -> return SCR
                "SDG" -> return SDG
                "SEK" -> return SEK
                "SGD" -> return SGD
                "SHP" -> return SHP
                "SLL" -> return SLL
                "SOS" -> return SOS
                "SRD" -> return SRD
                "SSP" -> return SSP
                "STN" -> return STN
                "SVC" -> return SVC
                "SYP" -> return SYP
                "SZL" -> return SZL
                "TJS" -> return TJS
                "TMT" -> return TMT
                "TND" -> return TND
                "TOP" -> return TOP
                "TTD" -> return TTD
                "TWD" -> return TWD
                "TZS" -> return TZS
                "UGX" -> return UGX
                "UYI" -> return UYI
                "UYU" -> return UYU
                "UZS" -> return UZS
                "VEF" -> return VEF
                "VUV" -> return VUV
                "WST" -> return WST
                "XAF" -> return XAF
                "XCD" -> return XCD
                "XDR" -> return XDR
                "XOF" -> return XOF
                "XPF" -> return XPF
                "YER" -> return YER
                "ZAR" -> return ZAR
                "ZWL" -> return ZWL
            }

            try {
                return Currency(java.util.Currency.getInstance(code))
            } catch (e: Exception) {
                return KZT
            }
        }

        private fun buildSymbol(currency: java.util.Currency): String {
            return when {
                CURRENCY_SYMBOLS_LOCALE_INDEPENDENT.containsKey(currency.currencyCode) -> CURRENCY_SYMBOLS_LOCALE_INDEPENDENT[currency.currencyCode]!!
                else -> currency.symbol
            }
        }
    }
}