package com.binar.secondhand.data.source.remote.network

import android.os.Build.VERSION_CODES.P
import com.binar.secondhand.data.source.remote.response.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("access_token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTUwNTAyMjh9.7qa0dZ7WPFi9tE4L9LPlRprqLReQXju9VzYsw8elKlI")
    @GET("seller/product")
    suspend fun getProduct(): Response<List<Product>>

}