package kz.app.ui_order_history.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderHistoryModule {

    @Provides
    @Singleton
    fun provideGetHistory(hitprintInteractors: HitprintInteractors) = hitprintInteractors.getOrderHistory

}