package com.binar.secondhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.ui.productlist.ProductListViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProductListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.repository.getListProduct().observe(this){ response ->
            when(response){
                is Resource.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    Toast.makeText(this, "succes load ${response.data?.size} product", Toast.LENGTH_SHORT).show()
                    Log.d("activity_main", "${response.data} ")
                }
                is Resource.Error -> {
                    Toast.makeText(this, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
            Log.d("activity_main", "${response.data} ")

        }

        Log.d("datares", "onCreate: ${viewModel.gelAllProduct.value}")
    }

}