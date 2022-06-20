package com.binar.secondhand.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginUser (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    val data : List<LoginUser>
)