package com.binar.secondhand.ui.akun

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.databinding.FragmentAkunBinding
import com.binar.secondhand.ui.auth.AuthActivity
import com.binar.secondhand.ui.productlist.ProductListViewModel
import com.binar.secondhand.ui.profile.CompleteAccountActivity
import com.binar.secondhand.utils.loadImage
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<AkunViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            tvUbahakun.setOnClickListener {
                startActivity(Intent(context, CompleteAccountActivity::class.java))
            }

//            tvSettings.setOnClickListener {
//                startActivity(Intent(context, SettingActivity::classs.java))
//            }

            tvLogout.setOnClickListener{
                SecondHandApp.getSharedPreferences().edit().clear().apply()
                startActivity(Intent(context, AuthActivity::class.java))
            }
        }

        observeDetailUser()
    }

    private fun observeDetailUser() {
        viewModel.getDetailUser().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    binding.apply {
                        ivUserphoto.loadImage(response.data?.image_url)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
            Log.d("activity_main", "${response.data} ")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}