package com.binar.secondhand.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.binar.secondhand.data.source.remote.network.ApiConfig
import com.binar.secondhand.data.source.remote.network.ApiService
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

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
        val registerUser = RegisterUser(0,fullname,email,password,"",+62,"")
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
}