package com.binar.secondhand.data.source.remote.network

import com.binar.secondhand.data.source.remote.response.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    //auth
    @GET("auth/user/{id}")
    suspend fun getDetailUser(@Path("id") id: Int): Response<GetProfileResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body loginInfo: LoginUser): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerUser(@Body registerUser: RegisterUser): Response<RegisterResponse>

    @PUT("auth/user/{id}")
    suspend fun editUser(@Path("id") id: Int,
                         @Body file: RequestBody
    ): Response<GetProfileResponse>


    //seller
    @GET("seller/product")
    suspend fun getProducts(): Response<Product>


}