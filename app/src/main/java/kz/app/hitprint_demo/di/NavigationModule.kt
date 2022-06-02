package kz.app.hitprint_demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.navigation.Navigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavigator() = Navigator()

}