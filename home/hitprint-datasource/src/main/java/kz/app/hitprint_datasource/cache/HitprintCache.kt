package kz.app.hitprint_datasource.cache

import com.squareup.sqldelight.db.SqlDriver
import kz.app.hitprint_domain.PayOrder
import kz.app.hitprintdatasource.cache.HitprintDBQueries

interface HitprintCache {

    suspend fun getOrderList(): List<PayOrder>

    suspend fun getOrder(id: Long): PayOrder?

    suspend fun getOrderByNumber(number: String): PayOrder?

    suspend fun insertOrder(order: PayOrder)

    suspend fun insertOrders(orders: List<PayOrder>)

    companion object Factory {

        fun build(sqlDriver: SqlDriver): HitprintCache {
            return HitprintCacheImpl(HitprintDatabase(sqlDriver))
        }
    }

}

class HitprintCacheImpl(
    private val database: HitprintDatabase
) : HitprintCache {

    private var queries: HitprintDBQueries = database.hitprintDBQueries

    override suspend fun getOrderList(): List<PayOrder> {
        return queries.selectAllOrders().executeAsList().map { it.toOrder() }
    }

    override suspend fun getOrder(id: Long): PayOrder {
        return queries.getOrder(id).executeAsOne().toOrder()
    }

    override suspend fun getOrderByNumber(number: String): PayOrder {
        return queries.getOrderByNumber(number).executeAsOne().toOrder()
    }

    override suspend fun insertOrder(order: PayOrder) {
        return queries.insertOrder(
            id = order.id,
            number = order.number,
            stage = order.stage.name,
            startDate = order.startDate,
            price = order.price,
            executionTime = order.executionTime,
            customerPhone = order.customerPhone,
            deliveryAddress = order.deliveryAddress,
            city = order.city,
            paymentMethod = order.paymentMethod?.name,
            businessId = order.businessId,
            businessPhone = order.businessPhone,
            businessName = order.businessName,
            businessAddress = order.businessAddress,
            kaspiQrUrl = order.kaspiQrUrl,
        )
    }

    override suspend fun insertOrders(orders: List<PayOrder>) {
        for (order in orders) {
            try {
                insertOrder(order)
            } catch (e: Exception) {
                e.printStackTrace()
                // if one has an error just continue with others
            }
        }
    }
}