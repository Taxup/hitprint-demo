package kz.app.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CentersModule {

    @Provides
    @Singleton
    fun provideGetCenters(interactors: HitprintInteractors): GetCenters = interactors.getCenters

    @Provides
    @Singleton
    fun provideSelectCenter(interactors: HitprintInteractors): SelectCenter = interactors.selectCenter

    @Provides
    @Singleton
    fun provideGetCenterReviews(interactors: HitprintInteractors): GetCenterReviews = interactors.getCenterReviews

    @Provides
    @Singleton
    fun provideUserLogout(interactors: HitprintInteractors): UserLogout = interactors.userLogout
}