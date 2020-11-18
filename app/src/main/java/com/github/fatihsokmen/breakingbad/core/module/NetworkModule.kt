package com.github.fatihsokmen.breakingbad.core.module

import com.github.fatihsokmen.breakingbad.core.coroutine.CoroutineDispatcherProvider
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule: Module = module {
    single {
        provideRetrofit(get())
    }
    single {
        provideOkHttpClient()
    }
    single<CoroutineDispatcherProvider> {
        CoroutineDispatcherProvider.Default()
    }
}

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder().apply {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(loggingInterceptor)
    }.build()

private fun provideRetrofit(okHttpClient: OkHttpClient) =
    // Url can be defined in a build flavour as buildConfigField
    Retrofit.Builder().baseUrl("https://breakingbadapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()