package kz.app.ui_upload_documents.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.hitprint_interactors.HitprintInteractors
import kz.app.hitprint_interactors.SelectDocs
import kz.app.hitprint_interactors.UploadDocuments
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UploadDocumentsModule {

    @Provides
    @Singleton
    fun provideUploadDocuments(interactors: HitprintInteractors): UploadDocuments = interactors.uploadDocuments

    @Provides
    @Singleton
    fun provideSelectDocs(interactors: HitprintInteractors): SelectDocs = interactors.selectDocs


}