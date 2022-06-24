package com.binar.secondhand.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.databinding.FragmentLoginBinding
import com.binar.secondhand.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<LoginUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            viewModel.loginUser(email,password).observe(requireActivity()){
                    response ->
                when (response) {
                    is Resource.Loading -> Toast.makeText(activity, "Tunggu sebentar", Toast.LENGTH_SHORT).show()
                    is Resource.Success -> {
                        Toast.makeText(activity, "Login success!", Toast.LENGTH_SHORT)
                            .show()
                        SecondHandApp.getSharedPreferences().edit().apply {
                            putInt(Constant.IS_LOGGED_ID,1)
                            putString(Constant.EMAIL, response.data?.email)
                            putString(Constant.TOKEN, response.data?.access_token)
                        }.apply()
                        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                    }
                    is Resource.Error -> {
                        Toast.makeText(activity, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                        Log.d("err", "error ${response.message}")
                        //bug error handling
                    }
                }
            }
        }

        binding.tvDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}