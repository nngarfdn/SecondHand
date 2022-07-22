package com.binar.secondhand.di

import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.data.api.service.ApiHelper
import com.binar.secondhand.data.api.service.ApiService
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://market-final-project.herokuapp.com/"
val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("access_token",
                        SecondHandApp.getSharedPreferences().getString("token","").toString(),)
                    .build()
                it.proceed(request)
            }
            .addInterceptor(
            ChuckerInterceptor.Builder(SecondHandApp.getContext())
                .collector(ChuckerCollector(SecondHandApp.getContext()))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    singleOf(::ApiHelper)

}