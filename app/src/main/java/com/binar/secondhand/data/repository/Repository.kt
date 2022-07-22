package com.binar.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.request.AddProductRequest
import com.binar.secondhand.data.source.remote.request.EditProfileRequest
import com.binar.secondhand.data.source.remote.response.AddProductResponse
import com.binar.secondhand.data.source.remote.response.GetAllCategoryResponseItem
import com.binar.secondhand.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.data.api.service.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.binar.secondhand.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.utils.Constant
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getAuth() = apiHelper.getAuth()

    suspend fun putAuth(
        fullname: RequestBody,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.putAuth(
        fullname,
        email,
        password,
        phone_number,
        address,
        city,
        image
    )

    suspend fun putPass(request: PutPassRequest) = apiHelper.putPass(request)

    suspend fun getNotification() = apiHelper.getNotification()
    suspend fun getBuyerOrder() = apiHelper.getBuyerOrder()
    suspend fun getProductId(id: Int) = apiHelper.getProductId(id)
    suspend fun getProductDetail(productId: Int) = apiHelper.getProductDetail(productId)
    suspend fun getUserProfile(userId: Int) = apiHelper.getUserProfile(userId)
    suspend fun postBuyerOrder(requestBuyerOrder: PostOrderRequest) = apiHelper.postBuyerOrder(requestBuyerOrder)
    suspend fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.postProduct(
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )

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

    fun getListCategory(): LiveData<Resource<List<GetAllCategoryResponseItem>>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(false).getAllCategory().let { response->
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



}