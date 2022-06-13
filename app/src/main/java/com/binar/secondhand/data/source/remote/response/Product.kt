package com.binar.secondhand.data.source.remote.response

data class Product(
    var base_price: Int = 0,
    var category: String = "",
    var createdAt: String = "",
    var id: Int = 0,
    var image_name: String = "",
    var image_url: String = "",
    var location: String = "",
    var name: String = "",
    var updatedAt: String = "",
    var user_id: Int = 0
)