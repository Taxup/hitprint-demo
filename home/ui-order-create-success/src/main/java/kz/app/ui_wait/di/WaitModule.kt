package kz.app.ui_wait.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaitModule {

    @Provides
    @Singleton
    fun provideGetOrderState(interactors: HitprintInteractors) = interactors.getOrderState

}