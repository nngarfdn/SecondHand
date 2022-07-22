package com.binar.secondhand.di

import com.binar.secondhand.data.repositories.AuthRepository
import com.binar.secondhand.data.repositories.BuyerRepository
import com.binar.secondhand.data.repositories.SellerRepository
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.kel2.data.repository.*
import com.binar.secondhand.data.repository.SearchHistoryRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    single { SellerRepository() }
    single { AuthRepository() }
    single { BuyerRepository() }

    singleOf(::Repository)

    singleOf(::LoginRepository)

    singleOf(::RegisterRepository)

    singleOf(::HomeRepository)

    singleOf(::ProductSaleListRepository)

    singleOf(::SearchHistoryRepository)


}