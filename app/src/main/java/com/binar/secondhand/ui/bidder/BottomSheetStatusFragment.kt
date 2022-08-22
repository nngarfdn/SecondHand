package com.binar.secondhand.ui.bidder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.data.api.model.seller.product.get.RequestUpdateStatusProduk
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentBottomSheetStatusBinding


class BottomSheetStatusFragment(
    private val token: String,
    private val produkId: Int,
    private val back: (stat : String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetStatusBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoPenawarViewModel by viewModels()
    private var status = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetStatusBinding.inflate(inflater, container, false)
        return binding.root
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        progressDialog.setCancelable(false)
        binding.btnKirimStatus.setOnClickListener {
            when (binding.radioGroup.checkedRadioButtonId) {
                R.id.rb_berhasil_terjual -> {
                    val request = RequestUpdateStatusProduk(
                        "seller"
                    )
                    status = "accepted"
                    viewModel.updateStatusProduk(token, produkId, request)
                }
                R.id.rb_batalkan_transaksi -> {
                    val request = RequestUpdateStatusProduk(
                        "available"
                    )
                    status = "declined"
                    viewModel.updateStatusProduk(token, produkId, request)
                }
            }

        }

        viewModel.responseStatus.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    if(it.data != null){
                        when(it.data.code()){
                            200 ->{
                                Handler().postDelayed({
                                    progressDialog.dismiss()
                                    dismiss()
                                    back(status)
                                },1000)
                            }
                            400 -> {
                                progressDialog.dismiss()
                                AlertDialog.Builder(requireContext())
                                    .setMessage("Produk Tidak Ditemukan!")
                                    .setPositiveButton("Ok") { dialog, _ ->
                                        dialog.dismiss()
                                        findNavController().popBackStack()
                                    }
                                    .show()
                            }
                        }
                    }
//                    viewModel.responseStatus.removeObservers(viewLifecycleOwner)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                            findNavController().popBackStack()
                        }
                        .show()
                }
                Status.LOADING -> {
                    progressDialog.show()
                }
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            val id: Int = binding.radioGroup.checkedRadioButtonId
            if (id != -1) {
                binding.btnKirimStatus.isEnabled = true
                binding.btnKirimStatus.backgroundTintList = requireContext().getColorStateList(R.color.dark_blue)
            }
        }
    }



}
