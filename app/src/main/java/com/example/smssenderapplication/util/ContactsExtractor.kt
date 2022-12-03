package com.example.smssenderapplication.util

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract

object ContactsExtractor {
    lateinit var contactPhoneNumbers: MutableMap<String, String>
        private set

    fun extractContacts(context: Context) {
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

    @SuppressLint("Range")
    private fun extractContactPhoneNumber(
        cursor: Cursor?,
        context: Context
    ) {
        cursor?.let { cursorHandler ->
            if (cursorHandler.count > 0) {
                while (cursorHandler.moveToNext()) {
                    val contactId = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val contactName = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    )
                    val phoneNumber =
                        (cursorHandler.getString(
                            (cursorHandler.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            ))
                        ))
                    this.contactPhoneNumbers[contactName] = phoneNumber
                }
            }
        }
    }

    fun applyFilter(filter: String): MutableList<String> {
        val filteredContacts = ArrayList<String>()
        this.contactPhoneNumbers.forEach { (key, value) ->
            if (key.contains(filter)) {
                filteredContacts.add(value)
            }
        }
        return filteredContacts
    }
}