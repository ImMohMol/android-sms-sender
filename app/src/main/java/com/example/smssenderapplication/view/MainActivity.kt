package com.example.smssenderapplication.view

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.SmsManager
import com.example.smssenderapplication.R
import com.example.smssenderapplication.base.SmsSenderBaseActivity
import com.example.smssenderapplication.databinding.ActivityMainBinding
import com.example.smssenderapplication.model.SendSmsInformation
import com.example.smssenderapplication.util.ContactsExtractor
import com.example.smssenderapplication.util.interfaces.ISmsSender
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.android.ext.android.inject

class MainActivity : SmsSenderBaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val smsSender: ISmsSender by inject()
    private lateinit var intent: PendingIntent
    private val SEND_SMS = "SMS_SENT"
    private var contactsCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        this.setListeners()
        this.getPermissions()
        this.setDeliveryIntent()
    }

    private fun setListeners() {
        this.binding.cbMultipleContacts.setOnCheckedChangeListener { _, isChecked ->
            this.binding.etReceiverPhoneNumber.isEnabled = !isChecked
        }

        this.binding.buttonSendMessage.setOnClickListener {
            val contacts =
                ContactsExtractor.applyFilter(this.binding.etFilterContactsNames.text.toString())
            if (this.binding.cbMultipleContacts.isChecked) {
                this.smsSender.sendSingleSms(
                    this,
                    SendSmsInformation(
                        this.binding.etSenderPhoneNumber.text.toString(),
                        this.binding.etFilterContactsNames.text.toString(),
                        this.binding.etMessage.text.toString(),
                        this.intent
                    ),
                    contacts[this.contactsCounter]
                )
            } else {
                this.smsSender.sendSingleSms(
                    this,
                    SendSmsInformation(
                        this.binding.etSenderPhoneNumber.text.toString(),
                        this.binding.etFilterContactsNames.text.toString(),
                        this.binding.etMessage.text.toString(),
                        this.intent
                    ),
                    this.binding.etReceiverPhoneNumber.text.toString()
                )
            }
        }
    }

    private fun setDeliveryIntent() {
        intent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(SEND_SMS),
            PendingIntent.FLAG_IMMUTABLE
        )

        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        this@MainActivity.createSnackBar(
                            "SMS Sent Successfully!",
                            this@MainActivity.binding.root,
                            Snackbar.LENGTH_LONG
                        )
                        contactsCounter++
                    }
                }
            }

        }, IntentFilter(SEND_SMS))
    }

    private fun getPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.SEND_SMS
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(permissonsReport: MultiplePermissionsReport?) {
                    permissonsReport?.let {
                        if (it.areAllPermissionsGranted()) {
                            ContactsExtractor.extractContacts(this@MainActivity)
                        } else {
                            this@MainActivity.createSnackBar(
                                getString(R.string.permissions_needed),
                                this@MainActivity.binding.root,
                                Snackbar.LENGTH_LONG
                            )
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    permissonToken: PermissionToken
                ) {
                    permissonToken.continuePermissionRequest()
                }
            }).check()
    }
}