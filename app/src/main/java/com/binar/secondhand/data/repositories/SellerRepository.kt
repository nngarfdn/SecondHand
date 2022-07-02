package com.binar.secondhand.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.request.AddProductRequest
import com.binar.secondhand.data.source.remote.response.AddProductResponse
import com.binar.secondhand.data.source.remote.response.ProductItem
import com.binar.secondhand.utils.Constant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class SellerRepository {

    fun getListProduct(): LiveData<Resource<List<ProductItem>>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getProducts().let { response->
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

    fun getProductById(id: String): LiveData<Resource<ProductItem>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getProductById(id).let { response->
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