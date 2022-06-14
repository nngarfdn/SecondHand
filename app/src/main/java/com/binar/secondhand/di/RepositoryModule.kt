package com.binar.secondhand.di

import com.binar.secondhand.data.repositories.AuthRepository
import com.binar.secondhand.data.repositories.SellerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { SellerRepository() }
    single { AuthRepository() }
}