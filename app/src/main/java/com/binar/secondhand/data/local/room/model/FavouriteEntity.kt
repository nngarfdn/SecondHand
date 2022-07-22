package com.binar.secondhand.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binar.secondhand.data.api.model.buyer.product.Category
import com.google.gson.annotations.SerializedName

@Entity
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val basePrice: Int,
    val imageUrl: String,
    val location: String,
    val name: String,

)
