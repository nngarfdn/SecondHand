package com.binar.secondhand.data.api.service

import com.binar.secondhand.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.data.api.model.seller.product.get.RequestApproveOrder
import com.binar.secondhand.data.api.model.seller.product.get.RequestUpdateStatusProduk
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ApiHelper(val apiService: ApiService) {
    suspend fun postLogin(request: PostLoginRequest) = apiService.postLogin(request)

    suspend fun postRegister(request: PostRegisterRequest) = apiService.postRegister(request)

    suspend fun getAuth() = apiService.getAuth()

    suspend fun putAuth(
        fullname: RequestBody,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.putAuth(
        fullname,
        email,
        password,
        phone_number,
        address,
        city,
        image
    )

    suspend fun putPass(request: PutPassRequest) = apiService.changePassword(request)

    suspend fun getNotification() = apiService.getNotification()

    suspend fun getBanner() = apiService.getBanner()

    suspend fun getProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null,
        page: Int=1,
        itemsPerPage: Int=20
    ) = apiService.getProduct(
        status,
        categoryId,
        searchKeyword,
        page,
        itemsPerPage
    )

    suspend fun getProductDetail(productId: Int) = apiService.getProductDetail(productId)
    suspend fun getUserProfile(userId: Int) = apiService.getUserProfile(userId)
    suspend fun getBuyerOrder() = apiService.getBuyerOrder()
    suspend fun postBuyerOrder(requestBuyerOrder: PostOrderRequest) = apiService.postBuyerOrder(requestBuyerOrder)
    suspend fun getCategory() = apiService.getCategory()
    suspend fun getProductId(id: Int) = apiService.getProductId(id)

    suspend fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.postProduct(
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )

    //product sale list
    suspend fun getSellerProduct() = apiService.getSellerProduct()

    suspend fun getSellerOrder() = apiService.getSellerOrder()

    suspend fun updateStatusProduk(token: String,produkId: Int, requestUpdateStatusProduk: RequestUpdateStatusProduk) = apiService.updateStatusProduk(token, produkId, requestUpdateStatusProduk)

    suspend fun approveOrder(token: String, id: Int, requestApproveOrder: RequestApproveOrder) = apiService.approveOrder(token, id, requestApproveOrder)

    suspend fun getSellerOrderById(token: String,orderId: Int) = apiService.getSellerOrderById(token,orderId)
}