package com.binar.secondhand.data.source.remote.request

import java.io.File

data class AddProductRequest (
    var name: String="",
    var description: String="",
    var base_price: String="",
    var category_ids: List<Int> = arrayListOf(),
    var location: String = "",
    var image: File
        )