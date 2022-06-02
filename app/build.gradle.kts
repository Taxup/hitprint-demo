plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions("appType")
    productFlavors {
        create("dev") {
            dimension = "appType"
            versionNameSuffix = "-dev"
            buildConfigField("String", "YANDEX_MAPKIT_KEY", "\"********-****-****-****-************\"")
        }
        create("prod") {
            dimension = "appType"

            buildConfigField("String", "YANDEX_MAPKIT_KEY", "\"********-****-****-****-************\"")
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.utils))
    implementation(project(Modules.navigation))
    implementation(project(Modules.components))
    implementation(project(Modules.uiLogin))
    implementation(project(Modules.uiOTP))
    implementation(project(Modules.hitprintInteractors))
    implementation(project(Modules.main))
    implementation(project(Modules.payment))
    implementation(project(Modules.service))
    implementation(project(Modules.uploadDocuments))
    implementation(project(Modules.uiPackages))
    implementation(project(Modules.uiDeliveryAddress))
    implementation(project(Modules.uiOrderCreateSuccess))
    implementation(project(Modules.uiOrderHistory))
    implementation(project(Modules.uiOrderDetails))
    implementation(project(Modules.uiRating))
    implementation(project(Modules.uiUserInfo))

    implementation(Coil.coil)
    implementation(Coil.gif)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Accompanist.animations)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigation)
    implementation(Compose.icons)

    implementation(Google.material)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(SqlDelight.androidDriver)

    //Lottie
    implementation(Lottie.lottie)

    //Firebase
    implementation(Firebase.analytics)
    implementation(Firebase.appCheck)
    implementation(Firebase.messaging)


    implementation(Yandex.map)
}










