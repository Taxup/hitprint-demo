package kz.app.hitprint_interactors

import kz.app.hitprint_domain.PrintService

class FilterServices {

    fun execute(
        current: List<PrintService>,
        serviceName: String
    ): List<PrintService> {
        return current.filter { it.name.contains(serviceName, ignoreCase = true) }
    }

}