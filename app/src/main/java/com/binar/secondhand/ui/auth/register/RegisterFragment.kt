package com.binar.secondhand.ui.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegisterUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegist.setOnClickListener {
            val namaLengkap = binding.etNamaRegister.text.toString()
            val email = binding.etEmailRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()
            registration(namaLengkap, email, password)
        }

        binding.tvMasuk.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


    }

    private fun registration(namaLengkap:String , email:String , password: String){
        viewModel.registerUser(namaLengkap, email, password).observe(requireActivity()){
                response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(activity, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    Toast.makeText(activity, "Register success!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is Resource.Error -> {
                    Toast.makeText(activity, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
        }
    }

}