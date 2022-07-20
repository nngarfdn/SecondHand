package com.binar.secondhand.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.MainActivity
import com.binar.secondhand.R
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.databinding.FragmentLoginBinding
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.login.LoginViewModel
import com.binar.secondhand.utils.Constant
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModel()

    companion object {
        const val LOGINUSER = "login_user"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()

        binding.tvDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

//        binding.tvSignup.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//        }

        binding.btnLogin.setOnClickListener {
            val loginPostRequest = PostLoginRequest(
                binding.etEmailLogin.text.toString(),
                binding.etPasswordLogin.text.toString()
            )

            if (binding.etEmailLogin.text.isNullOrEmpty() || binding.etPasswordLogin.text.isNullOrEmpty()) {
                Toast.makeText(context, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else {
                loginViewModel.postLogin(loginPostRequest)
            }
        }
    }

    private fun setUpObserver() {

        val preferences = this.requireActivity().getSharedPreferences(LOGINUSER, Context.MODE_PRIVATE)

        loginViewModel.loginPostResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
//                    binding.pbLoading.visibility = View.VISIBLE
//                    showDialog(context,           //context or this
//                        false,                    //dismiss dialog onBackPressed
//                        R.raw.loading       //lottie file json stored in res/raw
//                    )
                }

                Status.SUCCESS -> {
//                    binding.pbLoading.visibility = View.GONE
//                    hideDialog()
                    when (it.data?.code()) {
                        //jika code response 200
                        201 -> {
                            val data = it.data.body() //this is the data

                            val name = data?.name
                            val email = data?.email
                            val accesToken = data?.accessToken

//                            if (binding.cbPassword.isChecked) {
//                                // Save shared preferences
//                                val editor = preferences.edit()
//                                editor.putString(EMAIL, email)
//                                editor.putString(TOKEN, accesToken)
//                                editor.apply()
//                            }

                            // Bisa diganti pindah fragment
                            //Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()

                            //set access token setelah dapet dari login
                            getKoin().setProperty("access_token", accesToken.toString())
                            SecondHandApp.getSharedPreferences().edit().apply {
                                putInt(Constant.IS_LOGGED_ID,1)
                                putString(Constant.EMAIL, data?.email)
                                putString(Constant.TOKEN, data?.accessToken)
                            }.apply()

                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
//                            startActivity(Intent(context, com.binar.secondhand.kel2.ui.MainActivity::class.java))
                        }

                        401 -> {
                            showSnackbarWithAction("Email or Password Are Wrong", "Oke") {
                                // Do nothing
                            }
                        }

                        500 -> {
                            showSnackbar("Internal Service Error")
                        }
                    }
                }

                Status.ERROR -> {
//                    binding.pbLoading.visibility = View.GONE

                    val error = it.message
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}