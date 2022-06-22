package com.binar.secondhand.data.source.remote.network

import com.binar.secondhand.data.source.remote.response.GetProfileResponse
import com.binar.secondhand.data.source.remote.response.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {


    //auth
    @GET("auth/user/{id}")
    suspend fun getDetailUser(@Path("id") id: Int): Response<GetProfileResponse>

    //seller
    @GET("seller/product")
    suspend fun getProducts(): Response<Product>


}