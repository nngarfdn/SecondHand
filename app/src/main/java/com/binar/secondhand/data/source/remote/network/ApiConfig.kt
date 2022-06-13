package com.binar.secondhand.data.source.remote.network

import com.binar.secondhand.BuildConfig.BASE_URL
import com.binar.secondhand.SecondHandApp
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private val interceptor = HttpLoggingInterceptor().apply {

    }

    private fun okHttpClient(isPrivate: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {

                if (isPrivate) addInterceptor(Interceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(
                            "access_token",
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTUwNTAyMjh9.7qa0dZ7WPFi9tE4L9LPlRprqLReQXju9VzYsw8elKlI"
                        ).build()
                    chain.proceed(request)
                })

                addInterceptor(interceptor)
                addInterceptor(
                    ChuckerInterceptor.Builder(SecondHandApp.getContext())
                        .collector(ChuckerCollector(SecondHandApp.getContext()))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
                connectTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
            }.build()
    }


    fun getApiService(isPrivate: Boolean): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()

            .addInterceptor(loggingInterceptor)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .build()
        val retrofit by lazy {
            Retrofit.Builder()
                .client(okHttpClient(isPrivate))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit.create(ApiService::class.java)
    }


}