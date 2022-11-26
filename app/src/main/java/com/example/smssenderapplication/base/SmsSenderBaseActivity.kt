package com.example.smssenderapplication.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.smssenderapplication.util.SnackBarCreator
import com.example.smssenderapplication.util.interfaces.ISnackBarCreator
import org.koin.android.ext.android.inject

open class SmsSenderBaseActivity : AppCompatActivity() {
    private val snackBarCreator: ISnackBarCreator by inject()

    fun createSnackBar(message: String, view: View, length: Int) {
        this.snackBarCreator.createNormalSnackBar(
            messageText = message,
            view = view,
            length = length
        )
    }
}