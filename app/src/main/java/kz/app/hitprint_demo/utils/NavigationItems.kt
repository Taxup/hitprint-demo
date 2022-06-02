package kz.app.hitprint_demo.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.InternalCoroutinesApi
import kz.app.hitprint.ui.splash.SplashScreen
import kz.app.hitprint.ui.splash.SplashViewModel
import kz.app.main.ui.MainScreen
import kz.app.main.ui.MainViewModel
import kz.app.navigation.HomeScreen
import kz.app.navigation.Screen
import kz.app.payment.ui.PaymentScreen
import kz.app.payment.ui.PaymentViewModel
import kz.app.services.ui.ServiceListScreen
import kz.app.services.ui.ServiceViewModel
import kz.app.ui_login.ui.LoginScreen
import kz.app.ui_login.ui.LoginViewModel
import kz.app.ui_order_details.OrderDetailsScreen
import kz.app.ui_order_details.OrderDetailsViewModel
import kz.app.ui_order_history.ui.OrderHistoryScreen
import kz.app.ui_order_history.ui.OrderHistoryViewModel
import kz.app.ui_otp.ui.OTPScreen
import kz.app.ui_otp.ui.OtpViewModel
import kz.app.ui_packages.ui.PackagesScreen
import kz.app.ui_packages.ui.PackagesViewModel
import kz.app.ui_rating.RatingScreen
import kz.app.ui_rating.RatingViewModel
import kz.app.ui_upload_documents.ui.UploadDocumentsScreen
import kz.app.ui_upload_documents.ui.UploadDocumentsViewModel
import kz.app.ui_user_address.ui.DeliveryAddressScreen
import kz.app.ui_user_address.ui.DeliveryAddressViewModel
import kz.app.ui_user_info.UserAddressScreen
import kz.app.ui_user_info.UserAddressViewModel
import kz.app.ui_wait.ui.OrderCreateSuccessScreen
import kz.app.ui_wait.ui.OrderCreateSuccessViewModel


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.userAddressNavItem() {
    composable(
        route = Screen.UserAddress.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: UserAddressViewModel = hiltViewModel()
            UserAddressScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.deliveryAddressNavItem() {
    composable(
        route = "${Screen.DeliveryAddress.route}/{orderNumber}",
        arguments = Screen.DeliveryAddress.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: DeliveryAddressViewModel = hiltViewModel()
            DeliveryAddressScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.orderDetailsNavItem() {
    composable(
        route = "${Screen.OrderDetails.route}/{orderNumber}",
        arguments = Screen.OrderDetails.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: OrderDetailsViewModel = hiltViewModel()
            OrderDetailsScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeNavGraph() {
    navigation(
        startDestination = HomeScreen.Map.route,
        route = Screen.Home.route,
    ) {
        mapsNavItem()
        orderHistoryNavItem()
    }
}


@InternalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.splashNavItem() {
    composable(
        route = Screen.Splash.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: SplashViewModel = hiltViewModel()
            SplashScreen(viewModel::onTriggerEvent)
        }
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.loginNavItem() {
    composable(
        route = Screen.Login.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            )
        }
    )
}


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.otpNavItem(
    
) {
    composable(
        route = "${Screen.OTP.route}/{verificationId}",
        arguments = Screen.OTP.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: OtpViewModel = hiltViewModel()

            OTPScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            )
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.mapsNavItem() {
    composable(
        route = HomeScreen.Map.route,
        enterTransition = { fadeIn() + scaleIn(animationSpec = tween(150),0.92f) },
        exitTransition = { fadeOut(animationSpec = tween(350)) },
        content = {
            val viewModel: MainViewModel = hiltViewModel()

            MainScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            )
        }
    )
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.orderHistoryNavItem() {
    composable(
        route = HomeScreen.OrderHistory.route,
        enterTransition = { fadeIn() + scaleIn(animationSpec = tween(150),0.92f) },
        exitTransition = { fadeOut(animationSpec = tween(350)) },
        content = {
            val viewModel: OrderHistoryViewModel = hiltViewModel()
            OrderHistoryScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}


@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.paymentNavItem(imageLoader: ImageLoader) {
    composable(
        route = "${Screen.Payment.route}/{orderNumber}",
        arguments = Screen.Payment.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: PaymentViewModel = hiltViewModel()

            PaymentScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                imageLoader = imageLoader
            )
        }
    )
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.ratingNavItem() {
    composable(
        route = "${Screen.Rating.route}/{orderNumber}",
        arguments = Screen.Rating.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: RatingViewModel = hiltViewModel()
            RatingScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.servicesNavItem(imageLoader: ImageLoader) {
    composable(
        route = "${Screen.Service.route}/{orderNumber}",
        arguments = Screen.Service.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: ServiceViewModel = hiltViewModel()

            ServiceListScreen(
                state = viewModel.state.value,
                imageLoader = imageLoader,
                events = viewModel::onTriggerEvent,
            )
        }
    )
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.uploadDocumentsNavItem() {
    composable(
        route = "${Screen.UploadDocuments.route}/{orderNumber}",
        arguments = Screen.UploadDocuments.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: UploadDocumentsViewModel = hiltViewModel()

            UploadDocumentsScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.packagesNavItem() {
    composable(
        route = "${Screen.SelectPackages.route}/{orderNumber}",
        arguments = Screen.SelectPackages.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: PackagesViewModel = hiltViewModel()

            PackagesScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.orderCreateSuccessNavItem() {
    composable(
        route = "${Screen.OrderCreateSuccess.route}/{orderNumber}",
        arguments = Screen.OrderCreateSuccess.arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        content = {
            val viewModel: OrderCreateSuccessViewModel = hiltViewModel()

            OrderCreateSuccessScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            )
        }
    )
}
