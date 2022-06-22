package com.binar.secondhand.di

import com.binar.secondhand.ui.login.LoginUserViewModel
import com.binar.secondhand.ui.login.RegisterUserViewModel
import com.binar.secondhand.ui.productlist.ProductListViewModel
import com.binar.secondhand.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductListViewModel(get(),get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginUserViewModel(get()) }
    viewModel { RegisterUserViewModel(get()) }
}