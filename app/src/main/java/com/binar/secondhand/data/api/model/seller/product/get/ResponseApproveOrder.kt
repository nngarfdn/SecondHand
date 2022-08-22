package com.binar.secondhand.data.api.model.seller.product.get

import com.google.gson.annotations.SerializedName

data class ResponseApproveOrder(
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
