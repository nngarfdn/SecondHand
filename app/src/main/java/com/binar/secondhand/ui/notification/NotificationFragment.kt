package com.binar.secondhand.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.databinding.FragmentNotificationBinding
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.kel2.ui.notification.NotificationViewModel
import com.binar.secondhand.utils.Constant
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {

    private val notificationViewModel: NotificationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.activePage = R.id.main_notification

        val token = SecondHandApp.getSharedPreferences().getString(Constant.TOKEN, "")

        if (token == "") {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        } else {
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE
            binding.rvNotification.visibility = View.VISIBLE
            setUpObserver()
            notificationViewModel.getNotification()
        }

    }

    private fun setUpObserver(){

        notificationViewModel.notificationResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                    binding.rvNotification.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.rvNotification.visibility = View.VISIBLE
                    if (it.data?.body() != null) {
                        if (it.data.body()?.size == 0) {
                            binding.tvLogin.text = "Tidak ada notifikasi"
                            binding.ivLogin.visibility = View.VISIBLE
                            binding.tvLogin.visibility = View.VISIBLE
                            binding.rvNotification.visibility = View.GONE
                        } else {
                            showBidProduct(it.data.body())
                        }
                    }
                }
                Status.ERROR -> {
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun showBidProduct(data: GetNotificationResponse?) {
        val adapter = NotificationAdapter(
            object : NotificationAdapter.OnClickListener {
                override fun onClickItem(data: GetNotificationResponse.GetNotificationResponseItem) {
                    val id = data.id
                    findNavController().navigate(R.id.action_mainFragment_to_bidderFragment)
                }
            })

        adapter.submitData(data?.sortedByDescending {
            val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.ROOT)
            format.parse(it.createdAt)
        })
        binding.rvNotification.adapter = adapter
    }
}