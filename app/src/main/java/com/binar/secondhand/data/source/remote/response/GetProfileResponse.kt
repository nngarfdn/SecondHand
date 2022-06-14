package com.binar.secondhand.data.source.remote.response


data class GetProfileResponse(
    var address: String = "",
    var createdAt: String = "",
    var email: String = "",
    var full_name: String = "",
    var id: Int = 0,
    var image_url: String = "",
    var password: String = "",
    var phone_number: String = "",
    var updatedAt: String = ""
)