package com.binar.secondhand.ui.sale.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.databinding.ProductSaleListLayoutBinding
import com.binar.secondhand.data.api.model.seller.product.get.GetProductResponseItem
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.NumberFormat

class SellerProductAdapter(private val onClick: (GetProductResponseItem, Int) -> Unit) :
    ListAdapter<GetProductResponseItem, SellerProductAdapter.ViewHolder>(CommunityComparator()) {

    class ViewHolder(private val binding: ProductSaleListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentGetProductResponseItem: GetProductResponseItem,
            onClick: (GetProductResponseItem, Int) -> Unit,
            position: Int
        ) {

            if (position == 0) {
                binding.clAddProduct.visibility = View.VISIBLE
                binding.clProductItem.visibility = View.INVISIBLE
            } else {
                binding.clAddProduct.visibility = View.INVISIBLE
                binding.clProductItem.visibility = View.VISIBLE
            }

            binding.root.setOnClickListener {
                onClick(currentGetProductResponseItem, position)
            }

            Glide.with(binding.root).load(currentGetProductResponseItem.imageUrl)
                .into(binding.imvProductImage)
            binding.tvProductName.text = currentGetProductResponseItem.name
            binding.tvProductCategory.text = currentGetProductResponseItem.categories?.joinToString{
                it.name
            }
            val formatter: NumberFormat = DecimalFormat("#,###")
            val myNumber = currentGetProductResponseItem.basePrice
            val formattedNumber: String = formatter.format(myNumber).toString()
            binding.tvProductPrice.text = "Rp. ${formattedNumber}"
        }

    }

    class CommunityComparator : DiffUtil.ItemCallback<GetProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetProductResponseItem,
            newItem: GetProductResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GetProductResponseItem,
            newItem: GetProductResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductSaleListLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick, position)
    }

    fun submitData(listProduct: List<GetProductResponseItem>){
        val data = mutableListOf<GetProductResponseItem>()
        data.add( GetProductResponseItem(
            0,
            listOf(),
            "",
            0,
            "",
            "",
            "",
            "",
            "",
            0,
            ""
        ))
        data.addAll(listProduct)
        submitList(data)
    }

}