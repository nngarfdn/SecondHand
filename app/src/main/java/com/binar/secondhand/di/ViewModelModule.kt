package com.binar.secondhand.di

import com.binar.secondhand.ui.productlist.ProductListViewModel
import com.binar.secondhand.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductListViewModel(get(),get()) }
    viewModel { ProfileViewModel(get()) }
}