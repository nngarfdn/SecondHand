package com.binar.secondhand.data.source.remote.request

import android.net.Uri
import okhttp3.MultipartBody

data class EditProfileRequest(
    var full_name: String = "",
    var email: String = "",
    var password: String= "",
    var phone_number: Int?= 0,
    var address: String="",
    var image: String
)