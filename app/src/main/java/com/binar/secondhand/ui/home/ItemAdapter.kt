package com.binar.secondhand.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.binar.secondhand.callback.ProductListCallback
import com.binar.secondhand.data.source.remote.response.ProductItem
import com.binar.secondhand.databinding.FragmentItemBinding
import com.binar.secondhand.utils.CurrencyIndonesia
import com.binar.secondhand.utils.gone
import com.binar.secondhand.utils.loadImage
import com.binar.secondhand.utils.visible

import com.example.menu.placeholder.PlaceholderContent.PlaceholderItem

class ItemAdapter(
     val onItemClick: ProductListCallback
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(val view: FragmentItemBinding) : RecyclerView.ViewHolder(view.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(list: MutableList<ProductItem>) {
        differ.submitList(list) }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            val data = differ.currentList[position]

            if (data.id != 0){
                ivProduct.loadImage(data.image_url)
                tvProductTitle.text = data.name
                tvCategory.text = data.Categories.toString()
                tvPrice.text = CurrencyIndonesia.rp(data.base_price.toString())
                tvLocation.text = data.location
                root.setOnClickListener {
                    onItemClick.onClicked(data)
                }
            }
        }

    }

    override fun getItemCount(): Int = differ.currentList.size

}