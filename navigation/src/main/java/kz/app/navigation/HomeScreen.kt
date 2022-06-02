package kz.app.navigation

import androidx.navigation.NamedNavArgument

sealed class HomeScreen(
    override val route: String,
    override val arguments: List<NamedNavArgument>,
) : Screen(route, arguments) {

    object Map: HomeScreen(
        route = "map",
        arguments = emptyList()
    )

    object OrderHistory: HomeScreen(
        route = "order-history",
        arguments = emptyList()
    )

}