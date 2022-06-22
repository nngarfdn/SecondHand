package com.binar.secondhand.data.source.remote.response

data class RegisterUser(
    var id: Int?,
    var full_name:String,
    var email:String,
    var password:String,
    var address: String,
    var phoneNumber: Long?,
    var imageUrl: Any?
)

data class RegisterResponse(
    val data: List<User>
)
