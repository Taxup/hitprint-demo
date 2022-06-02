package kz.app.ui_login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.core.domain.Logger
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    @Named("login-logger")
    fun provideLogger(): Logger = Logger("login-logger")

//    @Provides
//    @Singleton
//    fun provideSendOtp(hitprintInteractors: HitprintInteractors) = hitprintInteractors.sendOTP

}