package com.binar.secondhand.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.ApiService
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.Product
import com.binar.secondhand.data.source.remote.response.ProductItem

class SellerRepository {

    private val clientPrivate = ApiConfig.getApiService(true)
    
    fun getListProduct(): LiveData<Resource<List<ProductItem>>> = liveData {
        emit(Resource.Loading())
        try {
            clientPrivate.getProducts().let { response->
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

}