package com.binar.secondhand.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.binar.secondhand.R
import com.binar.secondhand.data.source.remote.network.Resource
import com.binar.secondhand.data.source.remote.request.EditProfileRequest
import com.binar.secondhand.databinding.ActivityCompleteAccountBinding
import com.binar.secondhand.ui.productlist.ProductListViewModel
import com.binar.secondhand.utils.RealPathUtil
import com.binar.secondhand.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompleteAccountActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCompleteAccountBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<ProfileViewModel>()
    private var imageUri: Uri? = null
    private var realPath = ""
    private val REQUEST_CODE_PERMISSION = 100
    var selectedImg: Uri? = null

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    grantUriPermission(
                        packageName,
                        result,
                        FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                    )
                } catch (e: IllegalArgumentException) {
                    // on Kitkat api only 0x3 is allowed (FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION)
                    grantUriPermission(
                        packageName,
                        result,
                        FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    Log.e("", e.toString())
                }
                try {
                    var takeFlags = intent.flags
                    takeFlags =
                        takeFlags and (FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    result?.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
                } catch (e: SecurityException) {
                    Log.e("", e.toString())
                }
            }
            imageUri = result
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, result).toString();

            // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, result).toString();

            // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, result).toString();
            binding.imgProfile.setImageURI(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            imgProfile.setOnClickListener { openGallery() }
            btnSave.setOnClickListener {
                viewModel.completeAccount(
                    184, EditProfileRequest(
                        "Nanang Arifudin", "tester01@email.com", "123456", 0, "Bantul",
                        realPath
                    )
                ).observe(this@CompleteAccountActivity){ response ->
                    when (response) {
                        is Resource.Loading -> Toast.makeText(
                            this@CompleteAccountActivity,
                            "loading",
                            Toast.LENGTH_SHORT
                        ).show()
                        is Resource.Success -> {
                            Toast.makeText(
                                this@CompleteAccountActivity,
                                "success",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@CompleteAccountActivity,
                                "error ${response.message} ",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("err", "error ${response.message}")
                        }
                    }
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
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
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
                    FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                )
            } catch (e: IllegalArgumentException) {
                // on Kitkat api only 0x3 is allowed (FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION)
                grantUriPermission(
                    packageName,
                    result.data?.data,
                    FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                Log.e("", e.toString())
            }
            try {
                var takeFlags = intent.flags
                takeFlags =
                    takeFlags and (FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                result.data?.data?.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
            } catch (e: SecurityException) {
                Log.e("", e.toString())
            }
        }
        imageUri = result.data?.data
        if (Build.VERSION.SDK_INT < 11)
            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, result.data?.data).toString();

        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            realPath = RealPathUtil.getRealPathFromURI_API11to18(this, result.data?.data).toString();

        // SDK > 19 (Android 4.4)
        else
            realPath = RealPathUtil.getRealPathFromURI_API19(this, result.data?.data).toString();
        binding.imgProfile.setImageURI(result.data?.data)
    }


}