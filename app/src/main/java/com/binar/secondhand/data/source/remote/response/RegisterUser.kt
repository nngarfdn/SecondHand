package com.binar.secondhand.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterUser(
    val full_name: String,
    val email:String,
    val password:String,
    val phone_number: Int? = 0,
    val address: String? = "-",
    val image: Any? = null,
    val city: String? = "-"
)

data class RegisterResponse(
    val data: List<UserInfo>
)

data class UserInfo(
    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("full_name"    ) var fullName    : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("password"     ) var password    : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : Int?    = null,
    @SerializedName("address"      ) var address     : String? = null,
    @SerializedName("image_url"    ) var imageUrl    : String? = null,
    @SerializedName("city"         ) var city        : String? = null,
    @SerializedName("createdAt"    ) var createdAt   : String? = null,
    @SerializedName("updatedAt"    ) var updatedAt   : String? = null
)

