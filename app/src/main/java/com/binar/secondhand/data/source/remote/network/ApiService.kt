package com.binar.secondhand.data.source.remote.network

import com.binar.secondhand.data.source.remote.request.AddProductRequest
import com.binar.secondhand.data.source.remote.response.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    //auth
    @GET("auth/user/")
    suspend fun getDetailUser(): Response<GetProfileResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body loginInfo: LoginUser): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerUser(@Body registerUser: RegisterUser): Response<RegisterResponse>

    @PUT("auth/user")
    suspend fun editUser(@Body file: RequestBody
    ): Response<GetProfileResponse>


    //seller - product
    @GET("seller/product")
    suspend fun getProducts(): Response<Product>

    @POST("seller/product")
    suspend fun addProduct(@Body request: RequestBody): Response<AddProductResponse>

    @GET("/seller/product/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<ProductItem>

    //seller - category
    @GET("seller/category")
    suspend fun getAllCategory(): Response<GetAllCategoryResponse>

    //buyer
    @GET("buyer/product")
    suspend fun getProductsBuyer(): Response<Product>

    @GET("buyer/product/{id}")
    suspend fun getProductsDetail(
        @Path("id") id:Int
    ): Response<ProductItem>


}