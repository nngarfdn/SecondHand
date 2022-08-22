package com.binar.secondhand.ui.bidder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.seller.order.ResponseSellerOrderById
import com.binar.secondhand.data.api.model.seller.product.get.RequestApproveOrder
import com.binar.secondhand.data.api.model.seller.product.get.RequestUpdateStatusProduk
import com.binar.secondhand.data.api.model.seller.product.get.ResponseApproveOrder
import com.binar.secondhand.data.api.model.seller.product.get.ResponseUpdateStatusProduk
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class InfoPenawarViewModel (private val repository: Repository): ViewModel(){
    private var _responseOrder = MutableLiveData<Resource<ResponseSellerOrderById>>()
    val responseOrder : LiveData<Resource<ResponseSellerOrderById>> get() = _responseOrder

    private var _responseStatus = MutableLiveData<Resource<Response<ResponseUpdateStatusProduk>>>()
    val responseStatus : LiveData<Resource<Response<ResponseUpdateStatusProduk>>> get() = _responseStatus

    private var _responseApproveOrder = MutableLiveData<Resource<ResponseApproveOrder>>()
    val responseApproveOrder: LiveData<Resource<ResponseApproveOrder>> get() = _responseApproveOrder

    fun getOrderById(token: String, idOrder: Int){
        viewModelScope.launch {
            _responseOrder.postValue(Resource.loading())
            try {
                _responseOrder.postValue(Resource.success(repository.getSellerOrderById(token, idOrder)))
            } catch (e: Exception){
                _responseOrder.postValue(Resource.error(e.localizedMessage?:"Error occured"))
            }
        }
    }

    fun updateStatusProduk(token: String, produkId: Int, requestUpdateStatusProduk: RequestUpdateStatusProduk){
        viewModelScope.launch {
            _responseStatus.postValue(Resource.loading())
            try {
                _responseStatus.postValue(Resource.success(repository.updateStatusProduk(token, produkId, requestUpdateStatusProduk)))
            } catch (e: Exception){
                _responseStatus.postValue(Resource.error(e.localizedMessage?:"Error occured"))
            }
        }
    }

    fun updateOrderStatus(token: String, orderId: Int, requestApproveOrder: RequestApproveOrder){
        viewModelScope.launch {
            _responseApproveOrder.postValue(Resource.loading())
            try {
                _responseApproveOrder.postValue(Resource.success(repository.approveOrder(token, orderId, requestApproveOrder)))
            } catch (e: Exception){
                _responseOrder.postValue(Resource.error(e.localizedMessage?:"Error occured"))
            }
        }
    }
}

