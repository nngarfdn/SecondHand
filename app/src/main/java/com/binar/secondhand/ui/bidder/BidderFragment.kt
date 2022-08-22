package com.binar.secondhand.ui.bidder

import android.os.Bundle
import android.view.View
import android.app.AlertDialog
import android.app.ProgressDialog
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import androidx.fragment.app.viewModels
import com.binar.secondhand.R
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.data.api.model.seller.product.get.RequestApproveOrder
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentBidderBinding
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.detail.DetailProductViewModel
import com.binar.secondhand.ui.detail.DetailViewModel
import com.binar.secondhand.utils.Constant
import com.binar.secondhand.utils.currency
import com.binar.secondhand.utils.showToastSuccess
import com.binar.secondhand.utils.striketroughtText
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class BidderFragment : BaseFragment<FragmentBidderBinding>(FragmentBidderBinding::inflate) {

    private val viewModel: InfoPenawarViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        progressDialog.setCancelable(false)
        val bundlePenawar = arguments
        val idOrder = arguments?.getInt("ORDER_ID")

//        val token = SecondHandApp.getSharedPreferences().getString("token", "") ?: ""
//        getKoin().getProperty("access_token", "")
        val token = SecondHandApp.getSharedPreferences().getString(Constant.TOKEN, "")  ?: ""

        var status: String
        if (idOrder != null) {
            Toast.makeText(requireContext(), "Product $idOrder", Toast.LENGTH_SHORT).show()
            viewModel.getOrderById(token,idOrder)
        }else{
            Toast.makeText(requireContext(), "Product $idOrder Not Allowwed", Toast.LENGTH_SHORT).show()
        }

        viewModel.responseOrder.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    if(it.data != null){
                        it.data.let { data ->
                            binding.apply {
                                tvNamaPenawar.text = data.user.fullName
                                tvKotaPenawar.text = data.user.city
                                Glide.with(requireContext())
                                    .load("")
                                    .placeholder(R.drawable.default_image)
                                    .transform(CenterCrop(), RoundedCorners(12))
                                    .into(ivAvatarPenawar)
                                tvNamaProduk.text = data.productName
                                tvHargaAwal.apply {
                                    text = striketroughtText(this, currency(data.basePrice.toInt()))

                                }
                                tvHargaDitawar.text = getString(R.string.ditawar, currency(data.price))
                                val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
                                val date = format.parse(data.createdAt) as Date
                                tvTanggal.text = DateFormat.getDateInstance(DateFormat.FULL).format(date)
                                Glide.with(requireContext())
                                    .load(data.product.imageUrl)
                                    .transform(CenterCrop(), RoundedCorners(12))
                                    .into(ivProductImage)

                                when (data.status) {
                                    "accepted" -> {
                                        btnGroup.visibility = View.GONE
                                        btnGroupAccepted.visibility = View.VISIBLE
                                        if (data.product.status == "seller"){
                                            btnGroupAccepted.visibility = View.GONE
                                            tvPesan.visibility = View.VISIBLE
                                            tvPesan.text = getString(R.string.produk_sudah_terjual)
                                        }
                                    }
                                    "declined" -> {
                                        btnGroup.visibility = View.GONE
                                        btnGroupAccepted.visibility = View.GONE
                                        tvPesan.visibility = View.VISIBLE
                                        tvPesan.text = getString(R.string.tawaran_sudah_di_tolak)
                                    }
                                    "pending"->{
                                        btnGroupAccepted.visibility = View.GONE
                                        if (data.product.status == "seller"){
                                            btnGroup.visibility = View.GONE
                                            btnGroupAccepted.visibility = View.GONE
                                            tvPesan.visibility = View.VISIBLE
                                            tvPesan.text = getString(R.string.produk_sudah_laku)
                                        } else if (data.product.status == "sold"){
                                            btnGroupAccepted.visibility = View.GONE
                                        }
                                    }
                                }

                                btnTolak.setOnClickListener {
                                    if (data.status == "pending" && data.product.status == "sold") {
                                        Toast.makeText(requireContext(), "Product in transaction process", Toast.LENGTH_SHORT).show()
                                    }else {
                                        AlertDialog.Builder(requireContext())
                                            .setTitle("Pesan")
                                            .setMessage("Tolak Tawaran?")
                                            .setPositiveButton("Iya") { positive, _ ->
                                                status = "declined"
                                                val body = RequestApproveOrder(
                                                    status
                                                )
                                                if (token != null && idOrder != null) {
                                                    viewModel.updateOrderStatus(token, idOrder, body)
                                                    positive.dismiss()
                                                }
                                            }
                                            .setNegativeButton("Tidak") { negative, _ ->
                                                negative.dismiss()
                                            }
                                            .show()
                                    }
                                }

                                btnTerima.setOnClickListener {
                                    if (data.status == "pending" && data.product.status == "sold") {
                                        Toast.makeText(requireContext(), "Product in transaction process", Toast.LENGTH_SHORT).show()
                                    }else {
                                        AlertDialog.Builder(requireContext())
                                            .setTitle("Pesan")
                                            .setMessage("Terima Tawaran?")
                                            .setPositiveButton("Iya") { positive, _ ->
                                                status = "accepted"
                                                val body = RequestApproveOrder(
                                                    status
                                                )
                                                if (token != null && idOrder != null) {
                                                    viewModel.updateOrderStatus(token, idOrder, body)
                                                    positive.dismiss()
                                                }
                                            }
                                            .setNegativeButton("Tidak") { negative, _ ->
                                                negative.dismiss()
                                            }
                                            .show()
                                    }
                                }

                                btnHubungi.setOnClickListener {
                                    val bottomFragment = BottomSheetInfoPenawarFragment(
                                        data.user.fullName,
                                        data.user.city,
                                        data.user.phoneNumber,
                                        "",
                                        data.product.name,
                                        data.basePrice.toInt(),
                                        data.price,
                                        data.product.imageUrl
                                    )
                                    bottomFragment.show(parentFragmentManager, "Tag")
                                }

                                btnStatus.setOnClickListener {
                                    val bottomFragment = BottomSheetStatusFragment(
                                        token.toString(),
                                        data.productId,
                                        back = { stat ->
                                            if(stat == "accepted"){
                                                showToastSuccess(binding.root, "Berhasil Terjual", resources.getColor(R.color.succes))
                                            } else {
                                                showToastSuccess(binding.root, "Batal Transaksi", resources.getColor(R.color.succes))
                                            }
                                            findNavController().popBackStack()
                                        }
                                    )
                                    bottomFragment.show(parentFragmentManager, "Tag")
                                }

                                viewModel.responseApproveOrder.observe(viewLifecycleOwner){ resApprove ->
                                    when(resApprove.status){
                                        Status.SUCCESS -> {
                                            progressDialog.dismiss()
                                            if (resApprove.data != null){
                                                if (resApprove.data.status == "accepted"){
                                                    binding.btnGroup.visibility = View.GONE
                                                    binding.btnGroupAccepted.visibility = View.VISIBLE
                                                    val bottomFragment = BottomSheetInfoPenawarFragment(
                                                        data.user.fullName,
                                                        data.user.city,
                                                        data.user.phoneNumber,
                                                        "",
                                                        data.product.name,
                                                        data.basePrice.toInt(),
                                                        data.price,
                                                        data.product.imageUrl
                                                    )
                                                    bottomFragment.show(parentFragmentManager, "Tag")
                                                } else {
                                                    showToastSuccess(binding.root, "Tawaran ${data.user.fullName} di Tolak!", resources.getColor(R.color.succes))
                                                    binding.btnGroupAccepted.visibility = View.GONE
                                                    binding.btnGroup.visibility = View.GONE
                                                    binding.tvPesan.visibility = View.VISIBLE
                                                    binding.tvPesan.text = "Tawaran Di Tolak!"
                                                }
                                            }
                                        }
                                        Status.ERROR -> {
                                            progressDialog.dismiss()
                                            AlertDialog.Builder(requireContext())
                                                .setTitle("Pesan")
                                                .setMessage(it.message)
                                                .setPositiveButton("Iya") { positiveButton, _ ->
                                                    positiveButton.dismiss()
                                                }
                                                .show()
                                        }
                                        Status.LOADING -> {
                                            progressDialog.show()
                                        }
                                    }
                                }

                                btnBack.setOnClickListener {
                                    findNavController().popBackStack()
                                }
                            }
                        }
                        progressDialog.dismiss()
                    }
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




    }


}

