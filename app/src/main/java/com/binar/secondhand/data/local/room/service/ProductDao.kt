package com.binar.secondhand.data.local.room.service

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponseItem

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(products: List<GetProductResponseItem>)

    @Query(
        "SELECT * FROM product ORDER BY productId ASC"
    )
    fun getProducts(): PagingSource<Int, GetProductResponseItem>

    @Query("DELETE FROM product")
     fun clearProducts()

}
