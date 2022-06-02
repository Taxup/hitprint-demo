package kz.app.ui_user_info.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserAddressModule {

    @Provides
    @Singleton
    fun provideGetUserInfo(hitprintInteractors: HitprintInteractors) = hitprintInteractors.getUserInfo

    @Provides
    @Singleton
    fun provideGetCities(hitprintInteractors: HitprintInteractors) = hitprintInteractors.getCities

    @Provides
    @Singleton
    fun provideSetUserAddress(hitprintInteractors: HitprintInteractors) = hitprintInteractors.setUserAddress



}