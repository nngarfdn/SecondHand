package com.binar.secondhand.data.source.remote.network

import com.binar.secondhand.data.source.remote.response.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("seller/product")
    suspend fun getProducts(): Response<Product>

}