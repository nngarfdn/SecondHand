package com.binar.secondhand.ui.addproduct

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.callback.OnRadioSelectedListener
import com.binar.secondhand.data.source.remote.response.GetAllCategoryResponseItem
import com.binar.secondhand.databinding.BottomsheetRadiolistBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RadioListBottomSheet(
    private val data: List<GetAllCategoryResponseItem>,
    private val listener: OnRadioSelectedListener,
    private var title: String = "Radio List"
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetRadiolistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetRadiolistBinding.inflate(inflater, container, false)
        binding.rvListBottomsheet.apply {
            adapter = RadioListAdapter(context, data, listener, this@RadioListBottomSheet)
            layoutManager = LinearLayoutManager(context)
        }

        binding.txtTitleBottomRadio.text = title

        return binding.root
    }

    inner class RadioListAdapter(
        private val context: Context,
        private val list: List<GetAllCategoryResponseItem>,
        private val listener: OnRadioSelectedListener,
        private val dialogFragment: BottomSheetDialogFragment
    ) : RecyclerView.Adapter<RadioListAdapter.Holder>() {
        inner class Holder(view: View) : RecyclerView.ViewHolder(view)

        private var mSelectedItemPosition = -1
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            Holder(LayoutInflater.from(context).inflate(R.layout.item_radiobutton, parent, false))

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val radioButton = holder.itemView.findViewById<AppCompatRadioButton>(R.id.radio_item)
            radioButton.text = list[holder.position].name
            radioButton.isChecked = holder.position == mSelectedItemPosition

            radioButton.setOnClickListener {
                mSelectedItemPosition = holder.position
                listener.onRadioSelectedListener(name = list[holder.position].name, id= list[holder.position].id)
                notifyDataSetChanged()
                dialogFragment.dismiss()
            }
        }

        override fun getItemCount(): Int = list.size
    }
}