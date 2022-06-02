package kz.app.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    open val route: String,
    open val arguments: List<NamedNavArgument>
) {

    object Splash: Screen(
        route = "splash",
        arguments = emptyList(),
    )

    object Login: Screen(
        route = "login",
        arguments = emptyList(),
    )

    object OTP: Screen(
        route = "otp",
        arguments = listOf(
            navArgument("verificationId") {
                type = NavType.StringType
            }
        ),
    )

    object UserAddress: Screen(
        route = "user_address",
        arguments = emptyList()
    )

    object Home: Screen(
        route = "home",
        arguments = emptyList()
    )

    object Service: Screen(
        route = "service",
        arguments = listOf(
            navArgument("orderNumber") {
                type = NavType.StringType
            }
        ),
    )

    object UploadDocuments: Screen(
        route = "upload-documents",
        arguments = listOf(
            navArgument("orderNumber") {
                type = NavType.StringType
            }
        ),
    )

    object SelectPackages: Screen(
        route = "select-packages",
        arguments = listOf(
            navArgument("orderNumber") {
                type = NavType.StringType
            }
        ),
    )

    object DeliveryAddress: Screen(
        route = "delivery_address",
        arguments = listOf(
            navArgument("orderNumber") {
                type = NavType.StringType
            }
        ),
    )

    object OrderCreateSuccess: Screen(
        route = "order-create-success",
        arguments = listOf(
            navArgument("orderNumber") {
                type = NavType.StringType
            }
        ),
    )

    object Payment: Screen(
        route = "payment",
        arguments = emptyList(),
    )

    object Rating: Screen(
        route = "rating",
        arguments = listOf(
            navArgument("orderNumber") {
                type = NavType.StringType
            }
        ),
    )

    object OrderDetails: Screen(
        route = "order-details",
        arguments = listOf(
            navArgument("orderNumber") {
                type = NavType.StringType
            }
        ),
    )
}