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
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RlcjAxQGVtYWlsLmNvbSIsImlhdCI6MTY1NTYzMDIyMX0.2-OzROWKLGxc344tCy7xoW8Qer4foiuklWD4mmrBACE"
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


    fun getApiService(isPrivate: Boolean):ApiService{
        val retrofit by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(isPrivate))
                .build()
        }
        return retrofit.create(ApiService::class.java)
    }


}