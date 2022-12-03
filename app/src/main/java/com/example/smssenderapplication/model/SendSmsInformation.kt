package com.example.smssenderapplication.model

import android.app.PendingIntent
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendSmsInformation(
    val senderPhoneNumber: String,
    val filter: String,
    val message: String,
    val deliveryIntent: PendingIntent
) : Parcelable
