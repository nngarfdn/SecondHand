package com.binar.secondhand.ui.account

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentLogoutBinding

class LogoutFragment(private val onLogout: () -> Unit) : DialogFragment() {
    private var _binding : FragmentLogoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_radius)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        MainFragment.activePage = R.id.main_account

//        preferences =
//            this.requireActivity().getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)

        binding.tvKembali.setOnClickListener {
            dialog?.dismiss()
        }

        binding.tvYa.setOnClickListener {
            onLogout.invoke()
//            if (findNavController().currentDestination?.id == R.id.logoutFragment) {
//                findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)
//            }
            dialog?.dismiss()

// Still error menggunakan findnav controler
//            findNavController().navigate(R.id.action_logoutFragment_to_accountFragment)
//            preferences.edit().clear().apply()
//            getKoin().setProperty("access_token","")
//            findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)

//            findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}