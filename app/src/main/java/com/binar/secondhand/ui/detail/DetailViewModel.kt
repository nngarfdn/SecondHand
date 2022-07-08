package com.binar.secondhand.ui.detail

import androidx.lifecycle.ViewModel
import com.binar.secondhand.data.repositories.BuyerRepository

class DetailViewModel (private val buyerRepository: BuyerRepository) : ViewModel() {
    fun getProductDetail(id: Int) = buyerRepository.getProductsDetail(id)
}