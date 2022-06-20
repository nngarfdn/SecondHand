package com.binar.secondhand.ui.login

import androidx.lifecycle.ViewModel
import com.binar.secondhand.data.repositories.AuthRepository

class LoginUserViewModel(
    private val authRepository: AuthRepository): ViewModel() {
        fun loginUser(email:String, password:String) = authRepository.loginUser(email,password)
}