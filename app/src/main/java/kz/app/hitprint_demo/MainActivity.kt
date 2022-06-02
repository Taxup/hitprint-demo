package kz.app.hitprint_demo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.app.hitprint.ui.components.BottomNavigation
import kz.app.hitprint.ui.theme.HitprintTheme
import kz.app.hitprint.utils.*
import kz.app.navigation.HomeScreen
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import javax.inject.Inject

@SuppressWarnings("annotation")
@OptIn(
    ExperimentalMaterialApi::class,
    InternalCoroutinesApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalCoilApi::class,
    ExperimentalFoundationApi::class
)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mapKit: MapKit by lazy { MapKitFactory.getInstance() }

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var navigator: Navigator

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra("orderNumber")?.let { orderNumber ->
            navigator.navigateTo(Screen.Payment, orderNumber, popUpTo = HomeScreen.Map)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)

        setContent {
            HitprintTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberAnimatedNavController()
                SetupNavigation(navController)

                Scaffold(
                    bottomBar = {
                        BottomNavigation(navController)
                    }
                ) {
                    AnimatedNavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        builder = {
                            splashNavItem()

                            loginNavItem()
                            otpNavItem()

                            userAddressNavItem()
                            homeNavGraph()

                            servicesNavItem(imageLoader = imageLoader)
                            uploadDocumentsNavItem()
                            packagesNavItem()
                            deliveryAddressNavItem()
                            paymentNavItem(imageLoader = imageLoader)
                            orderCreateSuccessNavItem()

                            ratingNavItem()
                            orderDetailsNavItem()
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun SetupNavigation(navController: NavHostController) {
        LaunchedEffect("navigation") {
            navigator.sharedFlow.onEach { (screen, args, popUpTo, inclusive) ->
                val navOptionsBuilder: NavOptionsBuilder.() -> Unit = {
                    popUpTo?.let { popUpTo(popUpTo.route) { this.inclusive = inclusive }}
                }
                args?.let { navController.navigate("${screen.route}/${args}", navOptionsBuilder) } ?: navController.navigate(screen.route, navOptionsBuilder)
            }.launchIn(this)
            navigator.sharedBackFlow.onEach {
                if (navController.previousBackStackEntry == null) finish()
                else navController.navigateUp()
            }.launchIn(this)
        }
    }

    override fun onStop() {
        mapKit.onStop()
        super.onStop()
    }

    override fun onStart() {
        mapKit.onStart()
        super.onStart()
    }

}
