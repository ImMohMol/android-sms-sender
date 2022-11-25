package com.example.smssenderapplication.util

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract

object ContactsExtractor {
    lateinit var contactPhoneNumbers: MutableMap<String, String>
        private set

    fun extractContacts(namesShouldContain: String, context: Context) {
        this.contactPhoneNumbers = HashMap()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        this.extractContactPhoneNumber(cursor, context)
        cursor?.close()
    }

    private fun extractContactPhoneNumber(cursor: Cursor?, context: Context) {
        cursor?.let { cursorHandler ->
            if (cursorHandler.count > 0) {
                while (cursorHandler.moveToNext()) {
                    val contactId = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID).toInt()
                    )
                    val contactName = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).toInt()
                    )
                    val hasPhoneNumber =
                        (cursorHandler.getString((cursorHandler.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt())).toInt()
                    // this means that this contact has at least one phoneNumber
                    if (hasPhoneNumber > 0) {
                        val phoneNumberCursor = context.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID}=?",
                            arrayOf(contactId),
                            null
                        )
                        phoneNumberCursor?.let {
                            if (it.count > 0) {
                                while (it.moveToNext()) {
                                    val phoneNumberValue = it.getString(
                                        it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                            .toInt()
                                    )
                                    this.contactPhoneNumbers[contactName] = phoneNumberValue
                                }
                            }
                        }
                        phoneNumberCursor?.close()
                    }
                }
            }
        }
    }
}