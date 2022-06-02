package kz.app.ui_otp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.core.domain.Logger
import kz.app.hitprint_interactors.HitprintInteractors
import kz.app.ui_otp.BuildConfig
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OtpModule {

    @Provides
    @Singleton
    @Named("otp-logger")
    fun provideLogger(): Logger = Logger("otp-logger", isDebug = BuildConfig.DEBUG)

    @Provides
    @Singleton
    fun provideSendOtp(hitprintInteractors: HitprintInteractors) = hitprintInteractors.createUser
}