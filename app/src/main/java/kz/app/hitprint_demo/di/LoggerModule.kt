package kz.app.hitprint_demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.core.domain.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun provideLogger() = Logger.buildDebug("debug hitprint")
}