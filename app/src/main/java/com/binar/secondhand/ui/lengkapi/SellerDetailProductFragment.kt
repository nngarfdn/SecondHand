package com.binar.secondhand.ui.lengkapi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.SecondHandApp
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.request.AddProductRequest
import com.binar.secondhand.data.source.remote.response.GetAllCategoryResponseItem
import com.binar.secondhand.databinding.FragmentSellerDetailProductBinding
import com.binar.secondhand.kel2.data.resource.Status
import com.binar.secondhand.kel2.ui.base.BaseFragment
import com.binar.secondhand.kel2.ui.detail.BuyerPenawaranFragment
import com.binar.secondhand.kel2.ui.lengkapi.SellerDetailProductViewModel
import com.binar.secondhand.kel2.ui.main.MainFragment
import com.binar.secondhand.ui.addproduct.RadioListBottomSheet
import com.binar.secondhand.utils.Constant
import com.binar.secondhand.utils.RealPathUtil
import com.binar.secondhand.utils.uriToFile
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.lang.ref.WeakReference
import java.text.DecimalFormat


class SellerDetailProductFragment :
    BaseFragment<FragmentSellerDetailProductBinding>(FragmentSellerDetailProductBinding::inflate) {

    private val sellerDetailProductViewModel: SellerDetailProductViewModel by viewModel()

    private val REQUEST_CODE_PERMISSION = 100
    private var imageUri: Uri? = null
    private var realPath = ""
    private var listCategory = arrayListOf<GetAllCategoryResponseItem>()
    private var listCategoryName = arrayListOf<String>()

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity?.apply {
                try {
                    grantUriPermission(
                        packageName,
                        result.data?.data,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                    )
                } catch (e: IllegalArgumentException) {
                    grantUriPermission(
                        packageName,
                        result.data?.data,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    Log.e("", e.toString())
                }
                try {
                    var takeFlags = intent.flags
                    takeFlags =
                        takeFlags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    result.data?.data?.let {
                        contentResolver.takePersistableUriPermission(
                            it,
                            takeFlags
                        )
                        imageUri = result.data?.data

                        binding.btnTerbit.setOnClickListener {
                            binding.apply {
                                val name = etName.editText?.text.toString()
                                val price =
                                    etPrice.editText?.text.toString()
                                val city = etCity.editText?.text.toString()
                                val category = etCategory.editText?.text.toString()
                                var selectedCat = 0
                                for(cat in listCategory) {
                                    if(cat.name == category) {
                                        selectedCat = cat.id
                                    }
                                }
                                val description = etDescription.editText?.text.toString()
                                sellerDetailProductViewModel.addProduct(
                                    AddProductRequest(
                                        name, description, price, arrayListOf(selectedCat), city, uriToFile(
                                            result.data?.data!!, requireActivity()
                                        )
                                    )
                                ).observe(viewLifecycleOwner) { response ->
                                    when (response) {
                                        is Resource.Loading -> {}
                                        is Resource.Success -> {
                                            Snackbar.make(binding.snackbar, "Produk Berhasil Terbit", Snackbar.LENGTH_LONG)
                                                .setAction("x") {
                                                    // Responds to click on the action
                                                }
                                                .setBackgroundTint(resources.getColor(R.color.Green))
                                                .setActionTextColor(resources.getColor(R.color.white))
                                                .show()

                                            findNavController().navigate(R.id.action_sellerDetailProductFragment_to_productSaleListFragment)

                                        }
                                        is Resource.Error -> {
                                            Toast.makeText(
                                                requireActivity(),
                                                "error tambah ${response.message} ",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("err", "error ${response.message}")
                                        }
                                    }
                                }
//                    sellerDetailProductViewModel.postProduct(
//                        name = nameBody,
//                        base_price = priceBody,
//                        location = cityBody,
//                        category_ids = categoryBody,
//                        description = descriptionBody,
//                        image = imageBody
//                    )
                            }
                        }
                    }
                } catch (e: SecurityException) {
                    Log.e("", e.toString())
                }
            }
        }
        imageUri = result.data?.data
        if (Build.VERSION.SDK_INT < 11)
            realPath =
                activity?.let { RealPathUtil.getRealPathFromURI_BelowAPI11(it, result.data?.data).toString() }
                    .toString();

        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            realPath =
                RealPathUtil.getRealPathFromURI_API11to18(activity, result.data?.data).toString();

        // SDK > 19 (Android 4.4)
        else
            realPath =
                activity?.let { RealPathUtil.getRealPathFromURI_API19(it, result.data?.data).toString() }
                    .toString();
        binding.ivPhoto.setImageURI(result.data?.data)

    }

    private fun checkingPermissions() {
        if (activity?.let {
                isGranted(
                    it,
                    Manifest.permission.CAMERA,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_DOCUMENTS
                    ),
                    REQUEST_CODE_PERMISSION,
                )
            } == true
        ) {
            openGallery()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Permission Denied")
                .setMessage("Permission is denied, Please allow permissions from App Settings.")
                .setPositiveButton(
                    "App Settings"
                ) { _, _ ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", activity?.packageName ?: "", null)
                    intent.data = uri
                    startActivity(intent)
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                .show()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        val mimeTypes = arrayOf("image/png", "image/jpg", "image/jpeg")
        intent.action = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent.ACTION_OPEN_DOCUMENT
        } else {
            Intent.ACTION_PICK
        }
        intent.type = "*/*";
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var etMoney: EditText

        etMoney = binding.etPrice.editText!!

        //delimiter
        etMoney.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!s.toString().startsWith("Rp. ")) {
//                    etMoney.setMaskingMoney("Rp. ")
                    Selection.setSelection(etMoney.text, etMoney.text!!.length)
                }

            }
        })

        getActivity()?.getWindow()
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        val token = SecondHandApp.getSharedPreferences().getString(Constant.TOKEN, "")

        if (token == "") {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            binding.tvProduct.visibility = View.GONE
            binding.tvName.visibility = View.GONE
            binding.etName.visibility = View.GONE
            binding.tvPrice.visibility = View.GONE
            binding.etPrice.visibility = View.GONE
            binding.tvCity.visibility = View.GONE
            binding.etCity.visibility = View.GONE
            binding.tvKategori.visibility = View.GONE
            binding.etCategory.visibility = View.GONE
            binding.tvDescription.visibility = View.GONE
            binding.etDescription.visibility = View.GONE
            binding.tvPhoto.visibility = View.GONE
            binding.ivPhoto.visibility = View.GONE
            binding.btnPreview.visibility = View.GONE
            binding.btnTerbit.visibility = View.GONE

            Log.d("list", "token kosong")

            binding.btnLogin.setOnClickListener {

            }

        } else {
            Log.d("list", "token tidak kosong")
            binding.ivLogin.visibility = View.GONE
            binding.tvLogin.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE
        }

        val city = resources.getStringArray(R.array.city)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, city)
        binding.autoCompleteTv.setAdapter(arrayAdapter)

        sellerDetailProductViewModel.getAllCategory().observe(viewLifecycleOwner) {response ->
            when (response) {
                is Resource.Loading -> {}
                is Resource.Success -> {

                    if (response.data?.isNotEmpty() == true){
                        for (i in response.data){
                            listCategoryName.add(i.name)
                            listCategory.add(i)
                        }
                    }
                    val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, listCategoryName)
                    binding.autoCompleteTv2.setAdapter(arrayAdapter)
                }
                is Resource.Error -> {
                    Log.d("err", "error ${response.message}")
                }
            }
        }


        MainFragment.activePage = R.id.main_sell

        setUpObservers()

        binding.ivPhoto.setOnClickListener {
            checkingPermissions()
            openGallery()
        }

        binding.btnPreview.setOnClickListener {
            if (binding.etName.editText?.text.toString()
                    .isEmpty() || binding.etPrice.editText?.text.toString()
                    .isEmpty() || binding.etCity.editText?.text.toString()
                    .isEmpty() || binding.etCategory.editText?.text.toString()
                    .isEmpty() || binding.etDescription.editText?.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Lengkapi data terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
//                val actionToPreviewFragment = MainFragmentDirections.actionMainFragmentToPreviewFragment(
//                    name = binding.etName.editText?.text.toString(),
//                    price = binding.etPrice.editText?.text.toString().replace("Rp. ", "").replace(",", ""),
//                    location = binding.etCity.editText?.text.toString(),
//                    description = binding.etDescription.editText?.text.toString(),
//                    image = imageUri.toString(),
//                    category = binding.etCategory.editText?.text.toString()
//                )
//                findNavController().navigate(actionToPreviewFragment)
            }
            }

            binding.btnTerbit.setOnClickListener {
                binding.apply {
                    val name = etName.editText?.text.toString()
                    val price =
                        etPrice.editText?.text.toString().replace("Rp. ", "").replace(",", "")
                    val city = etCity.editText?.text.toString()
                    val category = etCategory.editText?.text.toString()
                    val description = etDescription.editText?.text.toString()

                    val imageFile = if (imageUri == null) {
                        null
                    } else {
//                    File(URIPathHelper.getPath(requireContext(), imageUri!!).toString())
                    }

                    val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                    val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
                    val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                    val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
                    val descriptionBody =
                        description.toRequestBody("text/plain".toMediaTypeOrNull())

//                    val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
//                val imageBody = requestImage?.let{
//                    MultipartBody.Part.createFormData("image", imageFile?.name, it)
//                }

                    imageUri?.let { it1 ->
                        context?.let { it2 ->
                            uriToFile(
                                it1, it2
                            )
                        }
                    }?.let { it2 ->
                        AddProductRequest(
                            name = binding.etName1.text.toString(),
                            description = binding.etDescription1.text.toString(),
                            base_price = binding.etPrice1.text.toString(),
                            category_ids = arrayListOf(7,2),
                            location = city,
                            it2
                        )
                    }?.let { it3 ->
                        sellerDetailProductViewModel.addProduct(
                            it3
                        )
                    }
//                    sellerDetailProductViewModel.postProduct(
//                        name = nameBody,
//                        base_price = priceBody,
//                        location = cityBody,
//                        category_ids = categoryBody,
//                        description = descriptionBody,
//                        image = imageBody
//                    )
                }
            }
    }

//    private fun openImagePicker() {
//        ImagePicker.with(this)
//            .crop()
//            .saveDir(
//                File(
//                    requireContext().externalCacheDir,
//                    "ImagePicker"
//                )
//            ) //Crop image(Optional), Check Customization for more option
//            .compress(1024)            //Final image size will be less than 1 MB(Optional)
//            .maxResultSize(
//                1080,
//                1080
//            )    //Final image resolution will be less than 1080 x 1080(Optional)
//            .createIntent { intent ->
//                startForProfileImageResult.launch(intent)
//            }
//    }

    private fun loadImage(uri: Uri?) {
        uri?.let {
            Glide.with(this)
                .load(it)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .into(binding.ivPhoto)
        }
    }

    private fun setUpObservers() {
        sellerDetailProductViewModel.sellerPostProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        201 -> {
                            Snackbar.make(
                                binding.snackbar,
                                "Produk Berhasil Terbit",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("x") {
                                    // Responds to click on the action
                                }
                                .setBackgroundTint(resources.getColor(R.color.Green))
                                .setActionTextColor(resources.getColor(R.color.white))
                                .show()
                        }
                        503 -> {
                            Snackbar.make(
                                binding.snackbar,
                                "Server sedang mengalami gangguan, harap coba lagi nanti.",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("x") {
                                    // Responds to click on the action
                                }
                                .setBackgroundTint(resources.getColor(R.color.Green))
                                .setActionTextColor(resources.getColor(R.color.white))
                                .show()
                        }
                        else -> {
                            Snackbar.make(
                                binding.snackbar,
                                "Terjadi kesalahan",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("x") {
                                    // Responds to click on the action
                                }
                                .setBackgroundTint(resources.getColor(R.color.Green))
                                .setActionTextColor(resources.getColor(R.color.white))
                                .show()
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Gagal terbit", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun EditText.setMaskingMoney(currencyText: String) {
//        set delimiter
        this.addTextChangedListener(object : BuyerPenawaranFragment.MyTextWatcher {
            val editTextWeakReference: WeakReference<EditText> =
                WeakReference<EditText>(this@setMaskingMoney)

            override fun afterTextChanged(editable: Editable?) {
                val editText = editTextWeakReference.get() ?: return
                val s = editable.toString()
                editText.removeTextChangedListener(this)
                val cleanString = s.replace("[Rp,. ]".toRegex(), "")
                val newval = currencyText + cleanString.monetize()

                editText.setText(newval)
                editText.setSelection(newval.length)
                editText.addTextChangedListener(this)
            }
        })
    }

    interface MyTextWatcher : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    fun String.monetize(): String = if (this.isEmpty()) "0"
    else DecimalFormat("#,###").format(this.replace("[^\\d]".toRegex(), "").toLong())
}

