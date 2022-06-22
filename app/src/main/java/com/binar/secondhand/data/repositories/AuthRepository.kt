package com.binar.secondhand.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.ApiService
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.GetProfileResponse
import com.binar.secondhand.data.source.remote.response.Product
import com.binar.secondhand.data.source.remote.response.ProductItem

class AuthRepository {
    fun test(){
        //Test push ke github
    }

    fun getDetailUser(id: Int): LiveData<Resource<GetProfileResponse>> = liveData {
        emit(Resource.Loading())
        try {
            ApiConfig.getApiService(true).getDetailUser(id).let { response->
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