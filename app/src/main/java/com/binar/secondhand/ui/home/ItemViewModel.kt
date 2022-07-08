package com.binar.secondhand.ui.home

import androidx.lifecycle.ViewModel
import com.binar.secondhand.data.repositories.AuthRepository
import com.binar.secondhand.data.repositories.BuyerRepository

class ItemViewModel (private val buyerRepository: BuyerRepository) : ViewModel() {
    val getProductBuyer  = buyerRepository.getListProductBuyer()
}