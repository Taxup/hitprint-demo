package kz.app.ui_packages.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PackageModule {

    @Provides
    @Singleton
    fun provideSelectPackage(interactors: HitprintInteractors) = interactors.selectPackage


    @Provides
    @Singleton
    fun provideGetPackages(interactors: HitprintInteractors) = interactors.getPackages

}