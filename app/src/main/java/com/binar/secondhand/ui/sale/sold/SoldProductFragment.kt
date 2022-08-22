package com.binar.secondhand.ui.sale.sold

import android.os.Bundle
import android.view.View
import com.binar.secondhand.databinding.FragmentSoldProductBinding
import com.binar.secondhand.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.sale.main.ProductSaleListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SoldProductFragment :
    BaseFragment<FragmentSoldProductBinding>(FragmentSoldProductBinding::inflate) {

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
        val filteredData = dataProduct?.filter { it.status == "seller" }
        val adapter = SoldProductAdapter { data ->
            showSnackbar("Produk Sudah Terjual")
        }
        if (dataProduct != null) {
            adapter.submitList(filteredData)
        }
        binding.rvSellerProduct.adapter = adapter
    }

}