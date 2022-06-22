package com.binar.secondhand.data.repositories

import android.R.attr.password
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.request.EditProfileRequest
import com.binar.secondhand.data.source.remote.response.GetProfileResponse
import com.binar.secondhand.utils.Constant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class AuthRepository {
    fun test() {
        //Test push ke github
    }

    fun getDetailUser(id: Int): LiveData<Resource<GetProfileResponse>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getDetailUser(id).let { response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    emit(Resource.Success(body!!))
                } else {
                    emit(Resource.Error(response.errorBody().toString()))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun editUser(id: Int,request: EditProfileRequest) = liveData {
        emit(Resource.Loading())
        val map: HashMap<String, RequestBody> = hashMapOf()
        try {
            map["full_name"] = request.full_name.toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["email"] = request.email.toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["password"] = request.password.toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["phone_number"] =
                request.phone_number.toString().toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["address"] = request.address.toRequestBody(Constant.MEDIA_TYPE_TEXT)

            val file = File(request.image)

            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                file.name,
                file.asRequestBody(Constant.MEDIA_TYPE_IMAGE)
            )

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("full_name", request.full_name)
                .addFormDataPart("email", request.email)
                .addFormDataPart("password", request.password)
                .addFormDataPart("phone_number", request.phone_number.toString())
                .addFormDataPart("address", request.address)
                .addPart(multipartBody)
                .build()
            ApiConfig.getApiService(true).editUser(id,requestBody).let { response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    emit(Resource.Success(body!!))
                } else {
                    emit(Resource.Error(response.errorBody().toString()))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }


}