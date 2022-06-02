package kz.app.hitprint_datasource.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kz.app.hitprint_datasource.network.exceptions.AppException
import kz.app.hitprint_datasource.network.model.*
import kz.app.hitprint_domain.*
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

interface HitprintService {

//    suspend fun sendOtp(phoneNumber: String): HttpResponse

    suspend fun createUpdateUser(phoneNumber: String, token: String): HttpResponse

    suspend fun setUserAddress(phoneNumber: String, city: String, address: String): HttpResponse

    suspend fun getCenterList(city: String): List<Center>

    suspend fun getServiceList(): List<PrintService>

    suspend fun getPackageList(): List<Package>


    suspend fun getUserInfo(phoneNumber: String) : User
    suspend fun getAllCities() : List<String>

    suspend fun uploadDocument(document: Document, onUpload: (Long, Long) -> Unit): String

    suspend fun startProcess(centerId: Long, phoneNumber: String): PayOrder
    suspend fun selectServices(orderNumber: String, serviceIds: List<Long>): PayOrder
    suspend fun selectDocs(orderNumber: String, docUuidAndCommentList: List<DocUuidAndComment>): PayOrder
    suspend fun selectPackage(orderNumber: String, packageId: Long): PayOrder
    suspend fun setDeliveryAddress(body: SetDeliveryAddressBody): PayOrder

    suspend fun getOrderState(orderNumber: String): PayOrder

    suspend fun finishOrder(orderNumber: String): HttpResponse

    suspend fun rateCenter(ratingDto: CreateUpdateRatingBody): HttpResponse

    suspend fun getOrderHistory(phoneNumber: String): List<PayOrder>

    suspend fun getCenterReviews(businessId: Long): List<Review>

    suspend fun setNewToken(phoneNumber: String, token: String)

    suspend fun logout(phoneNumber: String): HttpResponse

    companion object Factory {
        fun build(): HitprintService {
            return HitprintServiceImpl(
                httpClient = HttpClient(Android) {
                    expectSuccess = true
                    HttpResponseValidator {
                        handleResponseExceptionWithRequest { exception, _ ->
                            if (exception is SocketTimeoutException) {
                                throw TimeoutException("Превышен лимит времени на запрос")
                            }

                            if (exception is UnknownHostException || exception is ConnectException){
                                throw AppException("Похоже, что есть проблема с вашим подключением к Интернету. Пожалуйста, попробуйте еще раз.")
                            }

                            if (exception is IOException) throw AppException("Ошибка соединения, попробуйте позже")

                            if (exception !is ClientRequestException) return@handleResponseExceptionWithRequest
                            val exceptionResponse = exception.response
                            throw exceptionResponse.body<AppException>()
                        }
                    }
                    install(ContentNegotiation) {
                        json(
                            Json {
                                ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                            }
                        )
                    }
                    install(Logging) {
                        logger = Logger.SIMPLE
                        level = LogLevel.ALL
                    }
                    install(HttpTimeout) {
                        requestTimeoutMillis = 60000
                        connectTimeoutMillis = 60000
                        socketTimeoutMillis = 360000
                    }
                }
            )
        }
    }
}

private class HitprintServiceImpl(
    private val httpClient: HttpClient,
) : HitprintService {

//    override suspend fun sendOtp(phoneNumber: String): HttpResponse {
//        return httpClient.post {
//            url(EndPoints.SEND_OTP)
//            contentType(type = ContentType.Application.Json)
//            setBody(SendOtpBody(phoneNumber))
//        }
//    }

    override suspend fun createUpdateUser(phoneNumber: String, token: String): HttpResponse {
        return httpClient.post {
            url(EndPoints.CREATE_USER)
            contentType(type = ContentType.Application.Json)
            setBody(CreateUserBody(phoneNumber, token))
        }
    }

    override suspend fun setUserAddress(
        phoneNumber: String,
        city: String,
        address: String
    ): HttpResponse {
        return httpClient.post {
            url(EndPoints.SET_USER_INFO)
            contentType(type = ContentType.Application.Json)
            setBody(SetUserAddressBody(phoneNumber, city, address))
        }
    }

    override suspend fun getCenterList(city: String): List<Center> {
        return httpClient.get {
            url(EndPoints.CENTER_LIST)
            parameter("city", city)
        }.body<List<CenterDto>>().map { it.toDomain() }
    }

    override suspend fun getServiceList(): List<PrintService> {
        return httpClient.get {
            url(EndPoints.SERVICE_LIST)
        }.body<List<PrintServiceDto>>().map { it.toDomain() }
    }

    override suspend fun getPackageList(): List<Package> {
        return httpClient.get {
            url(EndPoints.PACKAGE_LIST)
        }.body<List<PackageDto>>().map { it.toDomain() }
    }

    override suspend fun getUserInfo(phoneNumber: String): User {
        return httpClient.get {
            url(EndPoints.GET_USER_INFO)
            parameter("phoneNumber", phoneNumber)
        }.body<UserDto>().toDomain()
    }

    override suspend fun getAllCities(): List<String> {
        return httpClient.get {
            url(EndPoints.CITY_LIST)
        }.body()
    }

    override suspend fun uploadDocument(document: Document, onUpload: (Long, Long) -> Unit) : String {
        return httpClient.submitFormWithBinaryData (
            url = EndPoints.UPLOAD_DOC,
            formData = formData {
                append("file", document.data.readBytes(), Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${document.filename}")
                })
            }
        ) {
            onUpload { bytesSentTotal, contentLength ->
                onUpload.invoke(bytesSentTotal, contentLength)
            }
        }.body()
    }


    override suspend fun startProcess(
        centerId: Long,
        phoneNumber: String
    ): PayOrder {
        return httpClient.post {
            url(EndPoints.START_SELECT_CENTER)
            contentType(type = ContentType.Application.Json)
            setBody(StartProcessBody(centerId, phoneNumber))
        }.body<PayOrderDto>().toDomain()
    }

    override suspend fun selectServices(orderNumber: String, serviceIds: List<Long>): PayOrder {
        return httpClient.post {
            url(EndPoints.SELECT_SERVICES)
            contentType(type = ContentType.Application.Json)
            setBody(SelectServicesBody(orderNumber, serviceIds))
        }.body<PayOrderDto>().toDomain()
    }

    override suspend fun selectDocs(orderNumber: String, docUuidAndCommentList: List<DocUuidAndComment>): PayOrder {
        return httpClient.post {
            url(EndPoints.SELECT_DOCS)
            contentType(type = ContentType.Application.Json)
            setBody(SelectDocsBody(orderNumber, docUuidAndCommentList.map { it.toDto() }))
        }.body<PayOrderDto>().toDomain()
    }

    override suspend fun selectPackage(orderNumber: String, packageId: Long): PayOrder {
        return httpClient.post {
            url(EndPoints.SELECT_PACKAGE)
            contentType(type = ContentType.Application.Json)
            setBody(SelectPackageBody(orderNumber, packageId))
        }.body<PayOrderDto>().toDomain()
    }

    override suspend fun setDeliveryAddress(body: SetDeliveryAddressBody): PayOrder {
        return httpClient.post {
            url(EndPoints.SET_DELIVERY_ADDRESS)
            contentType(type = ContentType.Application.Json)
            setBody(body)
        }.body<PayOrderDto>().toDomain()
    }

    override suspend fun getOrderState(orderNumber: String): PayOrder {
        return httpClient.get {
            url(EndPoints.GET_ORDER_STATE)
            contentType(type = ContentType.Application.Json)
            parameter("orderNumber", orderNumber)
        }.body<PayOrderDto>().toDomain()
    }

    override suspend fun finishOrder(orderNumber: String): HttpResponse {
        return httpClient.post {
            url(EndPoints.FINISH_ORDER)
            parameter("orderNumber", orderNumber)
        }
    }

    override suspend fun rateCenter(ratingDto: CreateUpdateRatingBody): HttpResponse {
        return httpClient.post {
            url(EndPoints.RATE_CENTER)
            contentType(type = ContentType.Application.Json)
            setBody(ratingDto)
        }
    }

    override suspend fun getOrderHistory(phoneNumber: String): List<PayOrder> {
        return httpClient.get {
            url(EndPoints.ORDER_HISTORY)
            parameter("phone", phoneNumber)
        }.body<List<PayOrderDto>>().map { it.toDomain() }
    }

    override suspend fun getCenterReviews(businessId: Long): List<Review> {
        return httpClient.get {
            url(EndPoints.CENTER_REVIEWS)
            parameter("businessId", businessId)
        }.body<List<RatingDto>>().map { it.toDomain() }
    }

    override suspend fun setNewToken(phoneNumber: String, token: String) {
        httpClient.post {
            url(EndPoints.SET_NEW_TOKEN)
            contentType(ContentType.Application.Json)
            setBody(SetNewTokenBody(phoneNumber, token))
        }
    }

    override suspend fun logout(phoneNumber: String): HttpResponse {
        return httpClient.post {
            url(EndPoints.LOGOUT)
            parameter("phoneNumber", phoneNumber)
        }
    }

}