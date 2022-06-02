package kz.app.hitprint_domain

enum class PaymentMethod(val value: String) {
    KaspiQR("Kaspi QR"), Card("Карта"), Cash("Наличные")
}