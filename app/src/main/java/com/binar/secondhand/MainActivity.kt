package com.binar.secondhand

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binar.secondhand.databinding.ActivityMainBinding
import com.binar.secondhand.ui.profile.CompleteAccountActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startActivity(Intent(this, CompleteAccountActivity::class.java))
    }
}