package kz.app.hitprint_demo.di

import android.app.Application
import android.os.Build.VERSION.SDK_INT
import kz.app.hitprint.R
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader = ImageLoader.Builder(app)
        .error(R.drawable.error_image)
        .placeholder(R.drawable.white_background)
        .componentRegistry {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder(app))
            } else {
                add(GifDecoder())
            }
        }
        .availableMemoryPercentage(.25)
        .crossfade(true)
        .build()


}