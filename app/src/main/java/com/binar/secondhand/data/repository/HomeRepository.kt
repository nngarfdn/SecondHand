package com.binar.secondhand.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.binar.secondhand.data.source.remote.ProductRemoteMediator
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.data.api.service.ApiHelper
import com.binar.secondhand.data.local.room.database.AppDatabase
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val apiHelper: ApiHelper,
                     private val appDatabase: AppDatabase
) {

    suspend fun getBanner() = apiHelper.getBanner()

    suspend fun getProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null
    ) = apiHelper.getProduct(
        status,
        categoryId,
        searchKeyword
    )

    fun getProductStream(categoryId: Int? = null): Flow<PagingData<GetProductResponseItem>> {

        val pagingSourceFactory = { appDatabase.productDao().getProducts() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = ProductRemoteMediator(apiHelper, appDatabase, categoryId),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }


    suspend fun getCategory() = apiHelper.getCategory()

    suspend fun getAuth() = apiHelper.getAuth()

    companion object{
        const val PAGE_SIZE = 20
    }
}