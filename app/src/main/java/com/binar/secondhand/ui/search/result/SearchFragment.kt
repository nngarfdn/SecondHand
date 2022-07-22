package com.binar.secondhand.ui.search.result

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentSearchBinding
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.search.result.SearchAdapter
import com.binar.secondhand.ui.search.result.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {


    private val searchViewModel by viewModel<SearchViewModel>()
    private val args: SearchFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchBarListener()
        setUpObserver()
        searchViewModel.getProduct(searchKeyword = args.querySearch)

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun setUpObserver() {
        searchViewModel.searchResponse.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING ->{

                }

                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showProductList(data)
                        }

                        else ->{
                            showSnackbar("Error occured: ${it.data?.code()}")
                        }
                    }
                }
                Status.ERROR -> {
                    showSnackbar("Error occured: ${it.data?.code()}")
                }
            }
        }
    }

    private fun showProductList(productResponse: GetProductResponse?) {
        binding.tvSearchResult.text = "Hasil pencarian untuk ${args.querySearch}," +
                " ${productResponse?.size?:0} ditemukan"
        val adapter = SearchAdapter {
            //onclick item
            val action = SearchFragmentDirections.actionSearchFragmentToDetailProductFragment()
            findNavController().navigate(action)
        }

        adapter.submitList(productResponse)

        binding.rvHomeProduct.adapter = adapter
    }

    private fun setUpSearchBarListener() {
        binding.etSearch.editText?.setOnFocusChangeListener { view, b ->
            if (b){
                searchProduct()
            }
        }
    }

    private fun searchProduct() {
        findNavController().navigate(R.id.action_searchFragment_to_searchPageFragment)
    }
}