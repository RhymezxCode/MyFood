package com.rhymezxcode.food.di

import com.rhymezxcode.food.data.api.FoodApiService
import com.rhymezxcode.food.data.repository.FetchAllFoodRepository
import com.rhymezxcode.food.data.repository.FetchAllFoodRepositoryImpl
import com.rhymezxcode.food.data.repository.FetchOneFoodRepository
import com.rhymezxcode.food.data.repository.FetchOneFoodRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindFetchOneFoodRepository(
        fetchOneFoodRepositoryImpl: FetchOneFoodRepositoryImpl
    ): FetchOneFoodRepository

    @Binds
    @Singleton
    abstract fun bindFetchAllFoodRepository(
        fetchAllFoodRepositoryImpl: FetchAllFoodRepositoryImpl
    ): FetchAllFoodRepository

    companion object {

        private const val BASE_URL = "https://assessment.vgtechdemo.com/api/"

        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // You can change the log level as per your needs
            }
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS) // Set appropriate timeouts
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient) // Set the OkHttpClient with logging interceptor
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        @Singleton
        fun provideFoodApiService(retrofit: Retrofit): FoodApiService {
            return retrofit.create(FoodApiService::class.java)
        }
    }
}
