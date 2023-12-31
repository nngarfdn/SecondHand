package com.binar.secondhand.ui.sale.product

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentSellerProductBinding
import com.binar.secondhand.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.sale.main.ProductSaleListViewModel
import com.binar.secondhand.ui.sale.product.SellerProductAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SellerProductFragment :
    BaseFragment<FragmentSellerProductBinding>(FragmentSellerProductBinding::inflate) {

    private val productSaleListViewModel by sharedViewModel<ProductSaleListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        productSaleListViewModel.getSellerProduct()


    }

    private fun setUpObserver(){
        productSaleListViewModel.getSellerProductResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
//                    binding.productShimmer.startShimmer()
                }

                Status.SUCCESS -> {

//                    binding.productShimmer.stopShimmer()
//                    binding.productShimmer.visibility = View.GONE

                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showProduct(data)
                        }

                        else -> {
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }

                Status.ERROR -> {
                    showSnackbar("${it.message}")
                }
            }
        }
    }

    private fun showProduct(dataProduct: GetSellerProductResponse?) {
        val adapter = SellerProductAdapter { data, position ->

            if (position == 0) {
                findNavController().navigate(R.id.action_mainFragment_to_sellerDetailProductFragment)
            } else {
//                val action =
//                  MainFragmentDirections.actionMainFragmentToDetailProductFragment(data.id)
//                findNavController().navigate(action)
            }

        }
        if (dataProduct != null) {
            adapter.submitData(dataProduct)
        }
        binding.rvSellerProduct.adapter = adapter
    }

}