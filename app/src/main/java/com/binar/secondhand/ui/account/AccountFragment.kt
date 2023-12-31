package com.binar.secondhand.ui.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.databinding.FragmentAccountBinding
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.ui.MainActivity
import com.binar.secondhand.ui.account.AccountViewModel
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.login.LoginFragment
import com.binar.secondhand.ui.main.MainFragment
import com.binar.secondhand.ui.profile.CompleteAccountActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    private val accountViewModel: AccountViewModel by viewModel()
    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_account

        preferences =
            this.requireActivity().getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)

//        val token = preferences.getString(LoginFragment.TOKEN, "")
        val token = SecondHandApp.getSharedPreferences().getString("token", "") ?: ""

        if (token == ""){
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            Glide.with(this)
                .load(R.drawable.round_camera)
                .into(binding.ivCam)
            binding.tvAkunSaya.visibility = View.GONE
            binding.ivElipseCam.visibility = View.GONE
            binding.ivCam.visibility = View.GONE
            binding.tvNama.visibility = View.GONE
            binding.tvNoHandphone.visibility = View.GONE
            binding.containerUbahAkun.visibility = View.GONE
            binding.containerPengaturanAkun.visibility = View.GONE
            binding.containerKeluar.visibility = View.GONE
            binding.lineOne.visibility = View.GONE
            binding.lineTwo.visibility = View.GONE
            binding.lineThree.visibility = View.GONE
            binding.tvVersion.visibility = View.GONE


        }else{
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE
            account()
            accountViewModel.getAuth()
        }

        binding.containerUbahAkun.setOnClickListener {
            // Ke fragment ubah akun

            if (token == ""){
                it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }else{
//                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
                startActivity(Intent(activity, CompleteAccountActivity::class.java))
            }
        }

        binding.containerPengaturanAkun.setOnClickListener {
            // Ke fragment pengaturan akun
            Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show()
//            it.findNavController().navigate(R.id.action_mainFragment_to_changePassFragment)
        }

        binding.containerKeluar.setOnClickListener {
            // Logout system
            val activity = it.context as MainActivity
            val dialogFragment = LogoutFragment{
                preferences.edit().clear().apply()
                getKoin().setProperty("access_token","")
                SecondHandApp.getSharedPreferences().edit().clear().apply()
                // Using find nav still error
                findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
//                if (it.findNavController().currentDestination?.id == R.id.logoutFragment) {
//                    it.findNavController().navigate(R.id.action_logoutFragment_to_mainFragment)
//                }
                }
            dialogFragment.show(activity.supportFragmentManager, null)
        }

    }

    private fun account(){
    accountViewModel.authGetResponse.observe(viewLifecycleOwner){
        when(it.status){

            Status.LOADING -> {
                //loading state, misal menampilkan progressbar
//                val shimmer =
//                    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
//                        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
//                        .setBaseAlpha(0.7f) //the alpha of the underlying children
//                        .setHighlightAlpha(0.6f) // the shimmer alpha amount
//                        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
//                        .setAutoStart(true)
//                        .build()
//                val shimmerDrawable = ShimmerDrawable().apply {
//                    setShimmer(shimmer)
//                }

                Glide.with(this)
                    .load(R.drawable.round_camera)

                    .transform(CircleCrop(), RoundedCorners(50))
                    .circleCrop()
                    .into(binding.ivCam)
            }

            Status.SUCCESS -> {
                when(it.data?.code()){
                    200 ->{


                        val token = preferences.getString(LoginFragment.TOKEN, "")

                            if (it.data.body()?.imageUrl == null){
//                            if (imageUri == null){
//                                    Glide.with(requireContext())
//                                        .load(it.data.body()?.imageUrl)
//                                        .placeholder(shimmerDrawable)
//                                        .transform(CenterCrop(), RoundedCorners(12))
//                                        .error(R.drawable.rectangle_camera)
//                                        .into(binding.ivCam)
                                Glide.with(this)
                                    .load(R.drawable.round_camera)

                                    .transform(CircleCrop(), RoundedCorners(50))
                                    .circleCrop()
                                    .into(binding.ivCam)
//                            }
                            }else{
                                Glide.with(requireContext())
                                    .load(it.data.body()?.imageUrl)

                                    .transform(CircleCrop(), RoundedCorners(50))
                                    .circleCrop()
                                    .into(binding.ivCam)
                            }
                            binding.tvNama.text = it.data.body()?.fullName
                            binding.tvNoHandphone.text = "+62"+ it.data.body()?.phoneNumber
                        }
                }
            }

            Status.ERROR ->{
//                binding.pbLoading.visibility = View.GONE
                val error = it.message
                Toast.makeText(requireContext(), "Error get Data : ${error}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

}