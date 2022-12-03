package com.example.smssenderapplication.util

import android.content.Context
import android.telephony.SmsManager
import com.example.smssenderapplication.model.SendSmsInformation
import com.example.smssenderapplication.util.interfaces.ISmsSender
import timber.log.Timber

class SmsSender : ISmsSender {
    override fun sendSingleSms(
        context: Context,
        information: SendSmsInformation,
        receiver: String
    ) {
        val smsManager = context.getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage(
            receiver,
            information.senderPhoneNumber,
            information.message,
            null,
            information.deliveryIntent
        )
    }

    override fun sendSmsToManyContacts(
        context: Context,
        information: SendSmsInformation,
        receivers: Map<String, String>
    ) {
        val smsManager = context.getSystemService(SmsManager::class.java)
        val messageParts = smsManager.divideMessage(information.message)
        receivers.forEach { (personName, phoneNumber) ->
            if (personName.contains(information.filter)) {
                val resultPhoneNumber = phoneNumber.replace(" ", "")
                Timber.i(phoneNumber)
                smsManager.sendMultipartTextMessage(
                    resultPhoneNumber,
                    information.senderPhoneNumber,
                    messageParts,
                    null,
                    null
                )
            }
        }
    }
}