package com.binar.secondhand.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.binar.secondhand.R
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.response.ProductItem
import com.binar.secondhand.databinding.FragmentDetailBinding
import com.binar.secondhand.utils.CurrencyIndonesia
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<DetailViewModel>()
    private var productList: ArrayList<ProductItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

//        val assets = listOf(
//            R.drawable.photo_one,
//            R.drawable.photo_two,
//            R.drawable.photo_three
//        )
//
//        createSlider(assets)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idProduct = arguments?.getInt("id_product")

        Toast.makeText(context, "$idProduct", Toast.LENGTH_SHORT).show()

        binding.nego.setOnClickListener {
            when {
                binding.nego.isEnabled -> {
                    binding.nego.isEnabled = false
                    binding.nego.text = "Menunggu respon penjual"
                }
            }
        }
        if (idProduct != null) {
            observeData(idProduct)
        }
    }
    private fun observeData(id: Int) {
        viewModel.getProductDetail(id).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    binding.apply {
                        produk.text = response.data?.name
                        kategori.text = response.data?.Categories.toString()
                        harga.text = CurrencyIndonesia.rp(response.data?.base_price.toString())
                        deskripsi.text = response.data?.name
                        penjelasan.text = response.data?.description.toString()
                        val assets = listOf(
                            response.data?.image_url,
                            response.data?.image_url
                        )
                        createSlider(assets)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, "error ${response.message} ", Toast.LENGTH_SHORT).show()
                    Log.d("err", "error ${response.message}")
                }
            }
        }
    }

    private fun createSlider(list: List<String?>) {
        val mAdapter = ImageDetailAdapter(list)

        binding.viewPager.adapter = mAdapter
        binding.viewPager.currentItem = 0
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

            }
        )
    }
}