package com.binar.secondhand.kel2.ui

import android.os.Bundle
import com.binar.secondhand.databinding.ActivityMainBinding
import com.binar.secondhand.kel2.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}