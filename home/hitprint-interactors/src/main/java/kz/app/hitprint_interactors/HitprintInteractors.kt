package kz.app.hitprint_interactors

import com.squareup.sqldelight.db.SqlDriver
import kz.app.hitprint_datasource.cache.HitprintCache
import kz.app.hitprint_datasource.cache.HitprintDatabase
import kz.app.hitprint_datasource.network.HitprintService

data class HitprintInteractors(
    val getCenters: GetCenters,
    val getServices: GetServices,
    val getPackages: GetPackages,
    val filterServices: FilterServices,
    val uploadDocuments: UploadDocuments,
//    val sendOTP: SendOTP,
    val createUser: CreateUser,
    val setUserAddress: SetUserAddress,

    val selectCenter: SelectCenter,
    val selectServices: SelectServices,
    val selectDocs: SelectDocs,
    val selectPackage: SelectPackage,
    val setDeliveryAddress: SetDeliveryAddress,

    val getOrderState: GetOrderState,
    val finishOrder: FinishOrder,
    val ratingCenter: RatingCenter,

    val getOrderHistory: GetOrderHistory,
    val getOrderDetails: GetOrderDetails,
    val getUserInfo: GetUserInfo,
    val getCities: GetCities,

    val getCenterReviews: GetCenterReviews,
    val setNewToken: SetNewToken,
    val userLogout: UserLogout
) {

    companion object Factory {

        fun build(sqlDriver: SqlDriver): HitprintInteractors {
            val service = HitprintService.build()
            val cache = HitprintCache.build(sqlDriver)
            return HitprintInteractors(
                getCenters = GetCenters(service),
                getServices = GetServices(service),
                getPackages = GetPackages(service),
                filterServices = FilterServices(),
                uploadDocuments = UploadDocuments(service),
//                sendOTP = SendOTP(service),
                createUser = CreateUser(service),
                setUserAddress = SetUserAddress(service),

                selectCenter = SelectCenter(service),
                selectServices = SelectServices(service),
                selectDocs = SelectDocs(service),
                selectPackage = SelectPackage(service),
                setDeliveryAddress = SetDeliveryAddress(service),

                getOrderState = GetOrderState(service),
                finishOrder = FinishOrder(service),
                ratingCenter = RatingCenter(service),

                getOrderHistory = GetOrderHistory(service, cache),
                getOrderDetails = GetOrderDetails(cache),
                getUserInfo = GetUserInfo(service),
                getCities = GetCities(service),


                getCenterReviews = GetCenterReviews(service),
                setNewToken = SetNewToken(service),
                userLogout = UserLogout(service)
            )
        }


        val schema: SqlDriver.Schema = HitprintDatabase.Schema

        const val dbName: String = "hitprint.db"
    }

}