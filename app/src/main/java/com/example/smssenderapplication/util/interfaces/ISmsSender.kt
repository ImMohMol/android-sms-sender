package com.example.smssenderapplication.util.interfaces

import android.content.Context
import com.example.smssenderapplication.model.SendSmsInformation

interface ISmsSender {
    fun sendSingleSms(
        context: Context,
        information: SendSmsInformation,
        receiver: String
    )

    fun sendSmsToManyContacts(
        context: Context,
        information: SendSmsInformation,
        receivers: Map<String, String>
    )
}