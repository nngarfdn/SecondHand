package com.binar.secondhand.utils

import android.app.ActionBar
import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.binar.secondhand.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun striketroughtText(tv: TextView, textChange: String): String {
    tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    return textChange
}

fun showToastSuccess(view: View, message: String, color: Int) {
    val snackBarView =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
    snackBarView.setAction(" ") {
        snackBarView.dismiss()
    }
    val textView =
        snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_close, 0)
    textView.compoundDrawablePadding = 16
    layoutParams.gravity = Gravity.TOP
    layoutParams.setMargins(32, 150, 32, 0)
    snackBarView.view.setPadding(24, 16, 0, 16)
    snackBarView.view.setBackgroundColor(color)
    snackBarView.view.layoutParams = layoutParams
    snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
    snackBarView.show()
}

fun currency(angka: Int): String {
    val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp "
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'

    kursIndonesia.decimalFormatSymbols = formatRp
    return kursIndonesia.format(angka).dropLast(3)
}