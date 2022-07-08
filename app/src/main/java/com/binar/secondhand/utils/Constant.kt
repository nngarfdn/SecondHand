package com.binar.secondhand.utils

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import okhttp3.MediaType.Companion.toMediaTypeOrNull

object Constant {
    const val TYPE_TEXT_PLAIN = "text/plain"
    val MEDIA_TYPE_TEXT = TYPE_TEXT_PLAIN.toMediaTypeOrNull()
    val MEDIA_TYPE_IMAGE = "image/jpg".toMediaTypeOrNull()
    val MEDIA_TYPE_PDF = "application/pdf".toMediaTypeOrNull()
    val COLOR_MATRIX_GRAYSCALE = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
    val IS_LOGGED_ID = "islogged"
    val EMAIL = "email"
    val TOKEN = "token"
    val ID_USER = "iduser"
}