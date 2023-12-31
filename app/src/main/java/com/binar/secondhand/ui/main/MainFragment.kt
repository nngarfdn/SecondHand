package com.binar.secondhand.ui.main

import android.os.Bundle
import android.view.View
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentMainBinding
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.home.HomeFragment
import com.binar.secondhand.ui.notification.NotificationFragment
import com.binar.secondhand.ui.sale.main.ProductSaleListFragment
import com.binar.secondhand.ui.account.AccountFragment
import com.binar.secondhand.ui.lengkapi.SellerDetailProductFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object{
        var activePage = 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.bottomMainFragment.setOnItemReselectedListener {
//            if (activePage == 0){
//                activity?.supportFragmentManager?.beginTransaction()
//                    ?.replace(R.id.main_fragment_host, HomeFragment())
//                    ?.commit()
//            }
//        }

        binding.bottomMainFragment.setOnItemSelectedListener {
            when(it.itemId){

                R.id.main_home -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, HomeFragment())
                        ?.commit()

                    true
                }

                R.id.main_notification -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, NotificationFragment())
                        ?.commit()

                    true
                }

                R.id.main_sell ->{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, SellerDetailProductFragment())
                        ?.commit()

                    true
                }

                R.id.main_sale_list -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, ProductSaleListFragment())
                        ?.commit()

                    true
                }

                R.id.main_account ->{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, AccountFragment())
                        ?.commit()

                    true
                }

                else -> false
            }
        }

        binding.bottomMainFragment.selectedItemId = if (activePage == 0){
            R.id.main_home
        }else{
            activePage
        }




    }

}