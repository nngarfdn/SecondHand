package com.binar.secondhand.ui.addproduct

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.binar.secondhand.callback.OnRadioSelectedListener
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.request.AddProductRequest
import com.binar.secondhand.data.source.remote.response.GetAllCategoryResponseItem
import com.binar.secondhand.databinding.ActivityAddProductBinding
import com.binar.secondhand.utils.RealPathUtil
import com.binar.secondhand.utils.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProductActivity : AppCompatActivity(), OnRadioSelectedListener {
    private val binding by lazy { ActivityAddProductBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<AddProductViewModel>()
    private val REQUEST_CODE_PERMISSION = 100
    private var imageUri: Uri? = null
    private var realPath = ""
    private var listCategory = arrayListOf<Int>()
    private var listCategoryName = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            actionBar.txtTitle.text = "Tambah Produk"
            imgProfile.setOnClickListener {
                checkingPermissions()
                openGallery()
            }

        }

        viewModel.getAllCategory().observe(this) {response ->
            when (response) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val bottomSheet = response.data?.let {
                        binding.edtCategory.setOnClickListener {

                        }
                        RadioListBottomSheet(
                            listener = this@AddProductActivity,
                            data = it,
                            title = "Category"
                        )
                    }
                    bottomSheet?.show(supportFragmentManager, bottomSheet.tag)
                }
                is Resource.Error -> {
                    Toast.makeText(
                        this@AddProductActivity,
                        "error tambah ${response.message} ",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("err", "error ${response.message}")
                }
            }
        }
    }

    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_DOCUMENTS
                ),
                REQUEST_CODE_PERMISSION,
            )
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
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
                }
            } catch (e: SecurityException) {
                Log.e("", e.toString())
            }
        }
        imageUri = result.data?.data
        if (Build.VERSION.SDK_INT < 11)
            realPath =
                RealPathUtil.getRealPathFromURI_BelowAPI11(this, result.data?.data).toString();

        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            realPath =
                RealPathUtil.getRealPathFromURI_API11to18(this, result.data?.data).toString();

        // SDK > 19 (Android 4.4)
        else
            realPath = RealPathUtil.getRealPathFromURI_API19(this, result.data?.data).toString();
        binding.imgProfile.setImageURI(result.data?.data)
        binding.apply {
            btnSave.setOnClickListener {
                viewModel.addProduct(
                    AddProductRequest(
                        "Mouse", "gaming gan", "1000000", arrayListOf(3, 2), "Bantul", uriToFile(
                            result.data?.data!!, this@AddProductActivity
                        )
                    )
                ).observe(this@AddProductActivity) { response ->
                    when (response) {
                        is Resource.Loading -> Toast.makeText(
                            this@AddProductActivity,
                            "loading",
                            Toast.LENGTH_SHORT
                        ).show()
                        is Resource.Success -> {
                            Toast.makeText(
                                this@AddProductActivity,
                                "success tambah data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@AddProductActivity,
                                "error tambah ${response.message} ",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("err", "error ${response.message}")
                        }
                    }
                }
            }
        }
    }


    override fun onRadioSelectedListener(name: String, id: Int) {
        listCategory.add(id)
        listCategoryName.add(name)

        var textCategory = ""

        for(cat in listCategoryName) {
            textCategory + " $cat"
        }
        binding.edtCategory.setText(textCategory)
    }
}
