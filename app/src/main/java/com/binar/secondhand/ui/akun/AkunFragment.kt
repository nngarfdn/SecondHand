package com.binar.secondhand.ui.akun

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.databinding.FragmentAkunBinding
import com.binar.secondhand.ui.auth.AuthActivity

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

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
            btnLogout.setOnClickListener {
                SecondHandApp.getSharedPreferences().edit().clear().apply()
                startActivity(Intent(context, AuthActivity::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}