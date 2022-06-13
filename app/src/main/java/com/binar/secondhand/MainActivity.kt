package com.binar.secondhand

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.ui.productlist.ProductListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<ProductListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeData()

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