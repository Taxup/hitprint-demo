package kz.app.hitprint_interactors

//
//class SendOTP(
//    private val service: HitprintService
//) {
//
//    fun execute(phoneNumber: String) = flow<DataState<Boolean>> {
//        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
//        try {
//            val response = service.sendOtp("+7$phoneNumber")
//            emit(DataState.Data(response.status.isSuccess()))
//        } catch (e: Exception) {
//            emit(DataState.Response(uiComponent = UIComponent.Dialog( description = e.message.toString())))
//        } finally {
//            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
//        }
//    }
//
//}