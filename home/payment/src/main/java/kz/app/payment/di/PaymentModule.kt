package kz.app.payment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

    @Provides
    @Singleton
    fun provideFinishOrder(interactors: HitprintInteractors) = interactors.finishOrder

}