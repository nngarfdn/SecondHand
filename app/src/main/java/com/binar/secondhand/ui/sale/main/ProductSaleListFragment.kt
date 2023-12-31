package com.binar.secondhand.ui.sale.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.databinding.FragmentProductSaleListBinding
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.main.MainFragment
import com.binar.secondhand.ui.sale.main.ProductSaleListViewModel
import com.binar.secondhand.ui.sale.main.ProductViewPagerAdapter
import com.binar.secondhand.ui.profile.CompleteAccountActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductSaleListFragment :
    BaseFragment<FragmentProductSaleListBinding>(FragmentProductSaleListBinding::inflate) {

    private val productSaleListViewModel: ProductSaleListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_sale_list

        val token = SecondHandApp.getSharedPreferences().getString("token", "")  ?: ""
        if (token == "") {
            binding.groupContent.visibility = View.GONE
            startActivity(Intent(activity, CompleteAccountActivity::class.java))
//            findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
        } else {
            binding.groupLogin.visibility = View.GONE
            binding.btnEditProfile.setOnClickListener {
//                it.findNavController().navigate(R.id.action_mainFragment_to_profileFragment2)
                startActivity(Intent(activity, CompleteAccountActivity::class.java))

            }
            setUpObserver()
            productSaleListViewModel.getAuth()
        }

        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.vpList.adapter = ProductViewPagerAdapter(this)
        TabLayoutMediator(binding.tabProductFilter,binding.vpList){ tab, pos ->
            when(pos){
                0 -> {
                    tab.text = "Product"
                    tab.setIcon(R.drawable.ic_product)
                }
                1 -> {
                    tab.text = "Diminati"
                    tab.setIcon(R.drawable.ic_fav)
                }
                2 -> {
                    tab.text = "Terjual"
                    tab.setIcon(R.drawable.ic_dollar)
                }
            }
        }.attach()
    }

    private fun setUpObserver() {
        productSaleListViewModel.authGetResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {

                            Glide.with(requireContext())
                                .load(it.data.body()?.imageUrl)
                                .transform(CenterCrop(), RoundedCorners(12))
                                .error(R.drawable.rectangle_camera)
                                .into(binding.ivUserPhoto)

                            binding.tvUserName.text = it.data.body()?.fullName
                            binding.tvUserCity.text = it.data.body()?.city
                        }
                    }
                }

                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(
                        requireContext(),
                        "Error get Data : $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}