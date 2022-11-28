package com.example.smssenderapplication.view

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smssenderapplication.R
import com.example.smssenderapplication.base.SmsSenderBaseActivity
import com.example.smssenderapplication.databinding.ActivityMainBinding
import com.example.smssenderapplication.util.ContactsExtractor
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : SmsSenderBaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        this.getPermissions()
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
                            ContactsExtractor.extractContacts("والدین کانون", this@MainActivity)
                            val phoneNumbers = StringBuilder()
                            ContactsExtractor.contactPhoneNumbers.forEach { (key, value) ->
                                phoneNumbers.append(String.format("%s -> %s\n", key, value))
                            }
                            this@MainActivity.binding.tvContactName.text = phoneNumbers.toString()
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