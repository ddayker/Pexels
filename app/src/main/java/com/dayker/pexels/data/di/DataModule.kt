package com.dayker.pexels.data.di

import android.app.DownloadManager
import android.content.Context
import androidx.room.Room
import com.dayker.pexels.data.datasource.local.ImageDatabase
import com.dayker.pexels.data.datasource.remote.PexelsApiService
import com.dayker.pexels.data.downloader.AndroidDownloader
import com.dayker.pexels.data.downloader.Downloader
import com.dayker.pexels.data.repository.ImageRepositoryImpl
import com.dayker.pexels.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): ImageDatabase {
            return Room.databaseBuilder(
                context,
                ImageDatabase::class.java,
                "images.db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideApi(): PexelsApiService {
            return Retrofit.Builder()
                .baseUrl(PexelsApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }

        @Singleton
        @Provides
        fun provideDownloadManager(@ApplicationContext context: Context): DownloadManager {
            return context.getSystemService(DownloadManager::class.java)
        }
    }

    @Binds
    abstract fun bindMovieRepository(
        repository: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    @Singleton
    abstract fun bindDownloader(androidDownloader: AndroidDownloader): Downloader

}