package com.binar.secondhand.data.api.model.seller.category.post

import com.google.gson.annotations.SerializedName

data class PostCategoryRequest(
    @SerializedName("name")
    val name: String,
)
