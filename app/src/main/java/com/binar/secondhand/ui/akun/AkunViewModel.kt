package com.binar.secondhand.ui.akun

import androidx.lifecycle.ViewModel
import com.binar.secondhand.data.repositories.AuthRepository
import com.binar.secondhand.data.repositories.SellerRepository

class AkunViewModel (private val authRepository: AuthRepository) : ViewModel() {
    fun getDetailUser()  = authRepository.getDetailUser()
}