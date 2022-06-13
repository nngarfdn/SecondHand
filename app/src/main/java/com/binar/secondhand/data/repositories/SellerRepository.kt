package com.binar.secondhand.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.ApiService
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.Product

class SellerRepository {


 fun getListProduct(): LiveData<Resource<List<Product>>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getProduct().let {
                if(it.isSuccessful){
                    val body = it.body()
                    val res = body?.map { it }
                    emit(Resource.Success(res!!.toSet().toList()))
                }else {
                    emit(Resource.Error(it.errorBody().toString()))
                }
            }
        }catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi Kesalahan"))
        }
    }

}