package kz.app.ui_user_address.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeliveryAddressModule {

    @Provides
    @Singleton
    fun provideSetDeliveryAddress(hitprintInteractors: HitprintInteractors) = hitprintInteractors.setDeliveryAddress


}