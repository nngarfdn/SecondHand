package com.binar.secondhand.kel2.ui.sale.bid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentBidProductBinding
import com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListViewModel
import com.binar.secondhand.ui.sale.bid.BidProductAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BidProductFragment :
    BaseFragment<FragmentBidProductBinding>(FragmentBidProductBinding::inflate) {

    private val productSaleListViewModel by sharedViewModel<ProductSaleListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
        productSaleListViewModel.getNotification()

    }

    private fun setUpObserver(){

        productSaleListViewModel.notificationResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                    binding.rvNotification.visibility = View.GONE
                }
                Status.SUCCESS -> {

                    binding.rvNotification.visibility = View.VISIBLE
                    if (it.data?.body() != null) {
                        if (it.data.body()?.size == 0) {
                            binding.ivEmpty.visibility = View.VISIBLE
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.rvNotification.visibility = View.GONE
                        } else {
                            binding.ivEmpty.visibility = View.GONE
                            binding.tvEmpty.visibility = View.GONE
                            binding.rvNotification.visibility = View.VISIBLE
                            showBidProduct(it.data.body())
                        }
                    }
                }
                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun showBidProduct(data: GetOrderResponse?) {
        val filteredData = data?.filter {
            it.status == "pending"
        }
        val adapter = BidProductAdapter(
            object : BidProductAdapter.OnClickListener {

                override fun onClickItem(data: GetOrderResponse.GetOrderResponseItem) {
                    val id = data.id
                    findNavController().navigate(R.id.action_mainFragment_to_bidderFragment)
                }
            })
        adapter.submitData(filteredData)
        binding.rvNotification.adapter = adapter
    }


}