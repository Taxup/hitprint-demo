package kz.app.hitprint_datasource.cache

import kz.app.hitprint_domain.OrderStage
import kz.app.hitprint_domain.PayOrder
import kz.app.hitprint_domain.PaymentMethod
import kz.app.hitprintdatasource.cache.Order_Entity

fun Order_Entity.toOrder(): PayOrder {
    return PayOrder(
        id = id,
        number = number,
        stage = OrderStage.valueOf(stage),
        startDate = startDate,
        price = price,
        executionTime = executionTime,
        customerPhone = customerPhone,
        deliveryAddress = deliveryAddress,
        city = city,
        paymentMethod = paymentMethod?.let { PaymentMethod.valueOf(it) },
        businessId = businessId,
        businessPhone = businessPhone,
        businessName = businessName,
        businessAddress = businessAddress,
        kaspiQrUrl = kaspiQrUrl,
    )
}