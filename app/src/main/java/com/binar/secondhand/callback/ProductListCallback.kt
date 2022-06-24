package com.binar.secondhand.callback

import com.binar.secondhand.data.source.remote.response.ProductItem

interface ProductListCallback {
    fun onClicked(item: ProductItem)
}