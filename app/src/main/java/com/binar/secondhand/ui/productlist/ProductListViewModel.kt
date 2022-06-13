package com.binar.secondhand.ui.productlist

import androidx.lifecycle.ViewModel
import com.binar.secondhand.data.repositories.SellerRepository


class ProductListViewModel(repository: SellerRepository) :ViewModel(){

    val gelAllProduct = repository.getListProduct()

}