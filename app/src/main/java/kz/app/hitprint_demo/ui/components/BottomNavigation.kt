package kz.app.hitprint_demo.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kz.app.hitprint.R
import kz.app.navigation.HomeScreen
import kz.app.utils.toTriple

@ExperimentalAnimationApi
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        HomeScreen.Map to R.string.map toTriple Icons.Rounded.Map,
        HomeScreen.OrderHistory to R.string.history toTriple Icons.Rounded.Menu
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    if (currentRoute in items.map { it.first.route }) {
        BottomNavigation(
            contentColor = Color(0xFF199450),
            backgroundColor = Color.White,
        ) {
            items.forEach { (item, titleId, icon) ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(id = titleId)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = titleId),
                            fontSize = 9.sp,
                        )
                    },
                    selectedContentColor = Color(0xFF199450),
                    unselectedContentColor = Color(0xFF199450).copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(HomeScreen.Map.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}