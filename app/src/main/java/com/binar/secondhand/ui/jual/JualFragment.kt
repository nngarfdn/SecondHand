package com.binar.secondhand.ui.jual

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.databinding.FragmentJualBinding
import com.binar.secondhand.ui.auth.AuthActivity
import com.binar.secondhand.ui.profile.CompleteAccountActivity
import com.binar.secondhand.utils.Constant

class JualFragment : Fragment() {

    private var _binding: FragmentJualBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJualBinding.inflate(inflater, container, false)
        val isLoggedIn = SecondHandApp.getSharedPreferences().getInt(Constant.IS_LOGGED_ID,0)

        if (isLoggedIn==0){
            startActivity(Intent(context, AuthActivity::class.java))
        }else{
            startActivity(Intent(context,CompleteAccountActivity::class.java))
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}