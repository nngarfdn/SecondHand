package com.binar.secondhand.di


import com.binar.secondhand.kel2.ui.account.AccountViewModel
import com.binar.secondhand.kel2.ui.detail.BuyerPenawaranViewModel
import com.binar.secondhand.kel2.ui.home.HomeViewModel
import com.binar.secondhand.kel2.ui.lengkapi.SellerDetailProductViewModel
import com.binar.secondhand.kel2.ui.login.LoginViewModel
import com.binar.secondhand.kel2.ui.notification.NotificationViewModel
import com.binar.secondhand.kel2.ui.pass.ChangePassViewModel
import com.binar.secondhand.kel2.ui.preview.PreviewViewModel
import com.binar.secondhand.kel2.ui.profile.ProfileViewModel
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListViewModel
import com.binar.secondhand.ui.addproduct.AddProductViewModel
import com.binar.secondhand.ui.akun.AkunViewModel
import com.binar.secondhand.ui.auth.login.LoginUserViewModel
import com.binar.secondhand.ui.auth.register.RegisterUserViewModel
import com.binar.secondhand.ui.detail.DetailProductViewModel
import com.binar.secondhand.ui.detail.DetailViewModel
import com.binar.secondhand.ui.home.ItemViewModel
import com.binar.secondhand.ui.productlist.ProductListViewModel
import com.binar.secondhand.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductListViewModel(get(),get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginUserViewModel(get()) }
    viewModel { RegisterUserViewModel(get()) }
    viewModel { AddProductViewModel(get()) }
    viewModel { ItemViewModel(get()) }
    viewModel { AkunViewModel(get()) }
    viewModel { DetailViewModel(get(),get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailProductViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { NotificationViewModel(get()) }

    viewModel { ProductSaleListViewModel(get()) }
    viewModel { PreviewViewModel(get()) }
//    viewModel { ChangePassViewModel(get()) }
//    viewModel { SearchPageViewModel(get()) }
    viewModel { AccountViewModel(get()) }

    viewModelOf(::RegisterViewModel)






    
    viewModelOf(::BuyerPenawaranViewModel)

    viewModelOf(::SellerDetailProductViewModel)


//    viewModelOf(::PreviewViewModel)
//
//    viewModelOf(::ProductSaleListViewModel)
//
//    viewModelOf(::ChangePassViewModel)
//
//    viewModelOf(::SearchPageViewModel)
//
//    viewModelOf(::SearchViewModel)
//
//    viewModelOf(::AccountViewModel)

}