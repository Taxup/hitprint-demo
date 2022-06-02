package kz.app.hitprint_demo.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HitprintInteractorsModule {

    @Provides
    @Singleton
    @Named("heroAndroidSqlDriver") // in case you have another SQL Delight db
    fun provideAndroidDriver(app: Application): SqlDriver = AndroidSqliteDriver(
        schema = HitprintInteractors.schema,
        context = app,
        name = HitprintInteractors.dbName
    )

    @Provides
    @Singleton
    fun provideHitprintInteractors(@Named("heroAndroidSqlDriver") sqlDriver: SqlDriver) = HitprintInteractors.build(sqlDriver)

    @Provides
    @Singleton
    fun provideSetNewToken(hitprintInteractors: HitprintInteractors) = hitprintInteractors.setNewToken

}