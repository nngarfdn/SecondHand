package com.binar.secondhand.ui.preview

import android.os.Bundle
import android.view.View
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentPreviewBinding
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.ui.base.BaseFragment
import com.binar.secondhand.ui.preview.PreviewViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(FragmentPreviewBinding::inflate){

//    val args: PreviewFragmentArgs by navArgs()

    private val previewViewModel: PreviewViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewViewModel.getAuth()

        profileAuth()

        terbit()

        binding.btnTerbit.setOnClickListener {
//            val name = args.name
//            val price = args.price
//            val description = args.description
//            val city = args.location
//            val category = args.category
//            val image = args.image.toUri()
//
//            val imageFile = if(image == null) {
//                null
//            }else{
//                File(URIPathHelper.getPath(requireContext(), image!!).toString())
//            }
//            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
//            val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
//            val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
//            val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
//            val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
//            val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
//            val imageBody = requestImage?.let{
//                MultipartBody.Part.createFormData("image", imageFile?.name, it)
//            }
//            previewViewModel.terbit(
//                name = nameBody,
//                base_price = priceBody,
//                location = cityBody,
//                category_ids = categoryBody,
//                description = descriptionBody,
//                image = imageBody
//            )
        }
    }

    private fun terbit() {
        previewViewModel.terbit.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Snackbar.make(binding.snackbar, "Produk Berhasil Terbit", Snackbar.LENGTH_LONG)
                        .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>(){
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                super.onDismissed(transientBottomBar, event)
                                requireActivity().onBackPressed()
                            }
                        })
                        .setAction("x") {
                            // Responds to click on the action
                            requireActivity().onBackPressed()
                        }
                        .setBackgroundTint(resources.getColor(R.color.Green))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()

                }
                Status.ERROR -> {
                    Snackbar.make(binding.snackbar, "Produk Gagal Terbit", Snackbar.LENGTH_LONG)
                        .setAction("x") {
                            // Responds to click on the action
                        }
                        .setBackgroundTint(resources.getColor(R.color.Green))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                }
                Status.LOADING -> {
                }
            }
        }
    }

    private fun profileAuth() {
       previewViewModel.getAuthResponse.observe(viewLifecycleOwner){
//           val name = args.name
//           val price = args.price
//           val description = args.description
//           val location = args.location
//           val category = args.category
//           val image = args.image.toUri()

//           when(it.status){
//               Status.LOADING -> {
//               }
//               Status.SUCCESS -> {
//                   when(it.data?.code()){
//                       200 -> {
//                           val formatter: NumberFormat = DecimalFormat("#,###")
//                           val myNumber = price.toInt()
//                           val formattedNumber: String = formatter.format(myNumber).toString()
//                            //formattedNumber is equal to 1,000,000
//                           binding.tvTitle.text = name
//                           binding.tvPrice.text = "Rp. $formattedNumber"
//                           binding.tvLocation.text = location
//                           binding.tvDesc.text = description
//                           binding.tvCategory.text = category
//                           binding.tvName.text = it.data?.body()?.fullName
//
//                           Glide.with(this)
//                               .load(it.data?.body()?.imageUrl)
//                               .centerCrop()
//                               .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
//                               .into(binding.imgProfile)
//
//                           Glide.with(this)
//                               .load(image)
//                               .centerCrop()
//                               .into(binding.ivBackdrop)
//                       }
//                       else -> {
//                           Toast.makeText(requireContext(), "${it.data?.message()}", Toast.LENGTH_SHORT).show()
//                       }
//                   }
//               }
//           }
       }
    }
}