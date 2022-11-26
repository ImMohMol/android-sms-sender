package com.example.smssenderapplication.util.interfaces

import android.view.View

interface ISnackBarCreator {
    fun createNormalSnackBar(messageText: String, view: View, length: Int)
}