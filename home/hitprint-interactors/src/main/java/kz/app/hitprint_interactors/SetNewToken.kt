package kz.app.hitprint_interactors

import kz.app.hitprint_datasource.network.HitprintService

class SetNewToken(private val service: HitprintService) {

    suspend fun execute(phoneNumber: String, token: String) {
        try {
            service.setNewToken(phoneNumber, token)
        } catch (e: Exception) {}
    }

}