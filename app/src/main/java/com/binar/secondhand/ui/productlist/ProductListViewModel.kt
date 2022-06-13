package com.binar.secondhand.ui.productlist

import android.graphics.Movie
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.repositories.SellerRepository
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductListViewModel :ViewModel(){

    val repository = SellerRepository()
    val movieDetail: MutableLiveData<Resource<Product>> = MutableLiveData()

    val gelAllProduct = repository.getListProduct()



}