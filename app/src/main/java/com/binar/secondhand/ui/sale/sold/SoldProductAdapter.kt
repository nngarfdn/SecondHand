package com.binar.secondhand.ui.sale.sold

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.databinding.ProductSaleListLayoutBinding
import com.binar.secondhand.data.api.model.seller.product.get.GetProductResponseItem
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.NumberFormat

class SoldProductAdapter(private val onClick: (GetProductResponseItem) -> Unit) :
    ListAdapter<GetProductResponseItem, SoldProductAdapter.ViewHolder>(ProductComparator()) {

    class ViewHolder(private val binding: ProductSaleListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentGetProductResponseItem: GetProductResponseItem,
            onClick: (GetProductResponseItem) -> Unit
        ) {

            binding.root.setOnClickListener {
                onClick(currentGetProductResponseItem)
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

    class ProductComparator : DiffUtil.ItemCallback<GetProductResponseItem>() {
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
        holder.bind(getItem(position), onClick)
    }

}