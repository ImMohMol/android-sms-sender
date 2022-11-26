package com.example.smssenderapplication.util

import android.content.Context
import android.util.LayoutDirection
import android.view.View
import androidx.core.view.ViewCompat
import com.example.smssenderapplication.util.interfaces.ISnackBarCreator
import com.google.android.material.snackbar.Snackbar

class SnackBarCreator : ISnackBarCreator {
    override fun createNormalSnackBar(messageText: String, view: View, length: Int) {
        val snackBar = Snackbar.make(view, messageText, length)
        ViewCompat.setLayoutDirection(snackBar.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        snackBar.show()
    }
}