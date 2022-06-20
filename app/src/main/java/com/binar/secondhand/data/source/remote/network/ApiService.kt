package com.binar.secondhand.data.source.remote.network

import com.binar.secondhand.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //auth
    @GET("auth/user/{id}")
    suspend fun getDetailUser(@Path("id") id: Int): Response<GetProfileResponse>

    //seller
    @GET("seller/product")
    suspend fun getProducts(): Response<Product>

    @POST("auth/login")
    suspend fun loginUser(@Body loginInfo: LoginUser): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerUser(@Body registerUser: RegisterUser): Response<RegisterResponse>

}