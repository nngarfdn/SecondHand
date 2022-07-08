package com.binar.secondhand.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.callback.ProductListCallback
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.ProductItem
import com.binar.secondhand.databinding.FragmentListHomeBinding
import com.binar.secondhand.ui.detail.DetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class ItemFragment : Fragment(), ProductListCallback {

    private var _binding: FragmentListHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ItemViewModel>()
//    private lateinit var productAdapter : ItemAdapter
    private val productAdapter by lazy { ItemAdapter(this@ItemFragment) }
    private var productList: ArrayList<ProductItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager =  LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        observeData()
        binding.list.adapter =  productAdapter
    }

    private fun observeData() {
        viewModel.getProductBuyer.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    response.data?.let { productList.addAll(it) }
                    productAdapter.submitData(productList)
                }
                is Resource.Error -> {
                    Toast.makeText(context, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
        }
    }

    override fun onClicked(item: ProductItem) {
        val bundle = bundleOf("id_product" to item.id)
        findNavController().navigate(R.id.action_itemFragment_to_detailFragment,bundle)
    }
}