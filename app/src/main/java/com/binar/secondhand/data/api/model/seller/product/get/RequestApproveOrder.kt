package com.binar.secondhand.data.api.model.seller.product.get

import com.google.gson.annotations.SerializedName

data class RequestApproveOrder(
    @SerializedName("status")
    val status: String
)