package com.binar.secondhand.ui.addproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.secondhand.R
import com.binar.secondhand.databinding.ActivitySelectCategoryBinding

class SelectCategoryActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySelectCategoryBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {

        }
    }
}