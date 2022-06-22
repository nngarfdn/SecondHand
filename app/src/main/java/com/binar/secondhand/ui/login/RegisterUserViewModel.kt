package com.binar.secondhand.ui.login

import androidx.lifecycle.ViewModel
import com.binar.secondhand.data.repositories.AuthRepository

class RegisterUserViewModel (
    private val authRepository: AuthRepository
): ViewModel() {
    fun registerUser(fullname:String ,email:String , password:String) = authRepository.registerUser(fullname,email,password)
}