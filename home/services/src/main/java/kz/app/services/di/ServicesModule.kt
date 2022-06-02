package kz.app.services.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import kz.app.hitprint_interactors.FilterServices
import kz.app.hitprint_interactors.GetServices
import kz.app.hitprint_interactors.SelectServices
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideGetServices(interactors: HitprintInteractors): GetServices = interactors.getServices



    @Provides
    @Singleton
    fun provideFilterServices(interactors: HitprintInteractors): FilterServices = interactors.filterServices



    @Provides
    @Singleton
    fun provideSelectServices(interactors: HitprintInteractors): SelectServices = interactors.selectServices



}