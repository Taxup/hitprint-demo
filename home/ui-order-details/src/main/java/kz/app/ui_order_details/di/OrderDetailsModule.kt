package kz.app.ui_order_details.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OrderDetailsModule {

    @Provides
    @Singleton
    fun provideGetOrderDetails(hitprintInteractors: HitprintInteractors) = hitprintInteractors.getOrderDetails

}