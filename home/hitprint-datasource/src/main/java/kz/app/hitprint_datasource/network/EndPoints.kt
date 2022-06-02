package kz.app.hitprint_datasource.network

object EndPoints {
    const val BASE_URL = "https://hitprint.herokuapp.com/api/v1"
    const val LOGOUT = "$BASE_URL/logout"
    const val SET_USER_INFO = "$BASE_URL/set-user-info"
    const val CENTER_LIST = "$BASE_URL/centers"
    const val SERVICE_LIST = "$BASE_URL/services"
    const val PACKAGE_LIST = "$BASE_URL/packages"
    const val CITY_LIST = "$BASE_URL/cities"
    const val UPLOAD_DOC = "$BASE_URL/upload-document"

    const val SEND_OTP = "$BASE_URL/phone-verification/start"
    const val CREATE_USER = "$BASE_URL/phone-verification/create-user"

    const val START_SELECT_CENTER = "$BASE_URL/select-center"
    const val SELECT_SERVICES = "$BASE_URL/select-services"
    const val SELECT_DOCS = "$BASE_URL/select-documents"
    const val SELECT_PACKAGE = "$BASE_URL/select-package"
    const val SET_DELIVERY_ADDRESS = "$BASE_URL/set-delivery-address"
    const val GET_USER_INFO = "$BASE_URL/user-info"

    const val GET_ORDER_STATE = "$BASE_URL/order-state"
    const val FINISH_ORDER = "$BASE_URL/finish-order"
    const val RATE_CENTER = "$BASE_URL/rate-center"

    const val ORDER_HISTORY = "$BASE_URL/order-history"

    const val CENTER_REVIEWS = "$BASE_URL/center-reviews"
    const val SET_NEW_TOKEN = "$BASE_URL/set-new-token"

}