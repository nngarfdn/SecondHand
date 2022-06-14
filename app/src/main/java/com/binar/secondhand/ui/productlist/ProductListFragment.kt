package com.binar.secondhand.ui.productlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binar.secondhand.R
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.databinding.FragmentProductListBinding
import com.binar.secondhand.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ProductListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            actionBar.txtTitle.text = getString(R.string.daftar_jual_saya)

        }

        observeData()
        observeDetailUser()
    }

    private fun observeDetailUser() {
        viewModel.getDetailUser(65).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    binding.apply {
                        txtName.text = response.data?.full_name
                        txtKota.text = response.data?.address
                        imgProfile.loadImage(response.data?.image_url)
                    }

                }
                is Resource.Error -> {
                    Toast.makeText(context, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
            Log.d("activity_main", "${response.data} ")
        }
    }

    private fun observeData() {
        viewModel.gelAllProduct.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    Toast.makeText(
                        context,
                        "succes load ${response.data?.size} product",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("activity_main", "${response.data} ")
                }
                is Resource.Error -> {
                    Toast.makeText(context, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
            Log.d("activity_main", "${response.data} ")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}