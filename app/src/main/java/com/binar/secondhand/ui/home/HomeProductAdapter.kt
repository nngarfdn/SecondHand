package com.binar.secondhand.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.databinding.HomeProductListLayoutBinding
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponseItem
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.NumberFormat

class HomeProductAdapter(private val onClick: (GetProductResponseItem) -> Unit) :
    PagingDataAdapter<UiModel.ProductItem, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val uiModel = getItem(position)) {
            is UiModel.ProductItem -> (holder as RepoViewHolder).bind(uiModel.products)
            else -> {}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = HomeProductListLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return RepoViewHolder(binding)
    }

    inner class RepoViewHolder(private val binding: HomeProductListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var product: GetProductResponseItem? = null

        fun bind(productResponseItem: GetProductResponseItem) {

            this.product = productResponseItem

            binding.root.setOnClickListener {
                onClick(productResponseItem)
            }

            Glide.with(binding.root).load(productResponseItem.imageUrl)
                .into(binding.imvProductImage)
            binding.tvProductName.text = productResponseItem.name
            binding.tvProductCategory.text = productResponseItem.categoryString
            val formatter: NumberFormat = DecimalFormat("#,###")
            val myNumber = productResponseItem.basePrice.toInt()
            val formattedNumber: String = formatter.format(myNumber).toString()
            //formattedNumber is equal to 1,000,000
            binding.tvProductPrice.text = "Rp. $formattedNumber"
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel.ProductItem>() {
            override fun areItemsTheSame(oldItem: UiModel.ProductItem, newItem: UiModel.ProductItem): Boolean {
                return (oldItem.products.name == newItem.products.name)
            }

            override fun areContentsTheSame(oldItem: UiModel.ProductItem, newItem: UiModel.ProductItem): Boolean =
                oldItem == newItem
        }
    }
}
