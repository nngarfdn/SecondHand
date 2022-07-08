package com.binar.secondhand.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.ProductItem

class BuyerRepository {
    fun getListProductBuyer(): LiveData<Resource<List<ProductItem>>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getProductsBuyer().let { response ->
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

    fun getProductsDetail(id: Int): LiveData<Resource<ProductItem>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getProductsDetail(id).let { response ->
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