package com.binar.secondhand.di

import com.binar.secondhand.ui.addproduct.AddProductViewModel
import com.binar.secondhand.ui.akun.AkunViewModel
import com.binar.secondhand.ui.auth.login.LoginUserViewModel
import com.binar.secondhand.ui.auth.register.RegisterUserViewModel
import com.binar.secondhand.ui.detail.DetailViewModel
import com.binar.secondhand.ui.home.ItemViewModel
import com.binar.secondhand.ui.productlist.ProductListViewModel
import com.binar.secondhand.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductListViewModel(get(),get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginUserViewModel(get()) }
    viewModel { RegisterUserViewModel(get()) }
    viewModel { AddProductViewModel(get()) }
    viewModel { ItemViewModel(get()) }
    viewModel { AkunViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}