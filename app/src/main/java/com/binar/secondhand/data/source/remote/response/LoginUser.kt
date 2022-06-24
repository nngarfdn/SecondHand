package com.binar.secondhand.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginUser (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,

)

data class LoginResponse(
    val access_token: String="",
    val name: String= "",
    val email: String="",
    val id: String="",
//    val data : List<LoginUser>
)