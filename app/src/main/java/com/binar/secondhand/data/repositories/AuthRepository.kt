package com.binar.secondhand.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.ApiService
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.request.AddProductRequest
import com.binar.secondhand.data.source.remote.request.EditProfileRequest
import com.binar.secondhand.data.source.remote.response.*
import com.binar.secondhand.utils.Constant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AuthRepository {

    fun getDetailUser(id: Int): LiveData<Resource<GetProfileResponse>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getDetailUser().let { response->
                if(response.isSuccessful){
                    val body = response.body()
                    emit(Resource.Success(body!!))
                }else {
                    emit(Resource.Error(response.errorBody().toString()))
                }
            }
        }catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun loginUser(email:String, password:String): LiveData<Resource<LoginResponse>> = liveData {
        emit(Resource.Loading())
        val loginInfo = LoginUser(email,password)
        try {
            ApiConfig.getApiService(false).loginUser(loginInfo).let { response->
                if(response.isSuccessful){
                    val body = response.body()
                    emit(Resource.Success(body!!))
                }else {
                    emit(Resource.Error(response.errorBody().toString()))
                }
            }
        }catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

    fun registerUser(fullname: String,email:String, password:String): LiveData<Resource<RegisterResponse>> = liveData {
        emit(Resource.Loading())
//        val registerUser = RegisterUser(fullname,email,password,12345678,"Jl. merdeka","","Jakarta")
        val registerUser = RegisterUser(fullname,email,password)
        try {
            ApiConfig.getApiService(false).registerUser(registerUser).let { response->
                if(response.isSuccessful){
                    val body = response.body()
                    emit(Resource.Success(body!!))
                }else {
                    emit(Resource.Error(response.errorBody().toString()))
                }
            }
        }catch (e: Exception) {
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

            val file = request.image

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
                .addFormDataPart("city", "bantul")
                .addPart(multipartBody)
                .build()
            ApiConfig.getApiService(true).editUser(requestBody).let { response ->
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

    fun addProduct(request: AddProductRequest): LiveData<Resource<AddProductResponse>> = liveData {
        emit(Resource.Loading())
        val map: HashMap<String, RequestBody> = hashMapOf()
        try {
            map["name"] = request.name.toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["description"] = request.description.toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["base_price"] = request.base_price.toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["category_ids"] =
                request.category_ids.toString().toRequestBody(Constant.MEDIA_TYPE_TEXT)
            map["location"] = request.location.toRequestBody(Constant.MEDIA_TYPE_TEXT)

            val file = request.image

            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                file.name,
                file.asRequestBody(Constant.MEDIA_TYPE_IMAGE)
            )

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", request.name)
                .addFormDataPart("description", request.description)
                .addFormDataPart("base_price", request.base_price)
                .addFormDataPart("category_ids", request.category_ids.toString())
                .addFormDataPart("location", request.location)
                .addFormDataPart("city", "bantul")
                .addPart(multipartBody)
                .build()
            ApiConfig.getApiService(true).addProduct(requestBody).let { response ->
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