package com.binar.secondhand.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.databinding.FragmentListHomeBinding
import com.example.menu.placeholder.PlaceholderContent

class ItemFragment : Fragment() {

    private var _binding: FragmentListHomeBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListHomeBinding.inflate(inflater, container, false)
        val adapter = ItemAdapter(PlaceholderContent.ITEMS)

        binding.list.layoutManager =  LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.list.adapter =  adapter

        return binding.root
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}