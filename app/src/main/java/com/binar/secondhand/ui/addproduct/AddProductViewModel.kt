package com.binar.secondhand.ui.addproduct

import androidx.lifecycle.ViewModel
import com.binar.secondhand.data.repositories.SellerRepository
import com.binar.secondhand.data.source.remote.request.AddProductRequest

class AddProductViewModel(
    private val productSellerRepository: SellerRepository
    ): ViewModel() {

    fun addProduct(request: AddProductRequest) = productSellerRepository.addProduct(request)

    fun getAllCategory() = productSellerRepository.getListCategory()

}