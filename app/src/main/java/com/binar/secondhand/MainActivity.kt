package com.binar.secondhand

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.databinding.ActivityMainBinding
import com.binar.secondhand.ui.productlist.ProductListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<ProductListViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeData()
        observeDetailUser()

    }

    private fun observeDetailUser() {
        viewModel.getDetailUser(65).observe(this) { response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    Toast.makeText(
                        this,
                        "succes load user : ${response.data?.full_name} ",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("activity_main", "${response.data} ")
                }
                is Resource.Error -> {
                    Toast.makeText(this, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
            Log.d("activity_main", "${response.data} ")
        }
    }

    private fun observeData() {
        viewModel.gelAllProduct.observe(this) { response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    Toast.makeText(
                        this,
                        "succes load ${response.data?.size} product",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("activity_main", "${response.data} ")
                }
                is Resource.Error -> {
                    Toast.makeText(this, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
            Log.d("activity_main", "${response.data} ")
        }
    }

}