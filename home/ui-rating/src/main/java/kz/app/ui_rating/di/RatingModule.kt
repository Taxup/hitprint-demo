package kz.app.ui_rating.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RatingModule {

    @Provides
    @Singleton
    fun provideRatingCenter(hitprintInteractors: HitprintInteractors) = hitprintInteractors.ratingCenter

}