package com.binar.secondhand.data.source.remote.response



class Product : ArrayList<ProductItem>()

data class ProductItem(
    var Categories: List<Any> = listOf(),
    var base_price: Int = 0,
    var createdAt: String = "",
    var description: Any = Any(),
    var id: Int = 0,
    var image_name: Any = Any(),
    var image_url: Any = Any(),
    var location: String = "",
    var name: String = "",
    var status: Any = Any(),
    var updatedAt: String = "",
    var user_id: Int = 0
)