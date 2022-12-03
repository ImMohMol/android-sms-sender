package com.example.smssenderapplication.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.smssenderapplication.util.interfaces.ISmsSender
import org.koin.android.ext.android.inject

class SmsSenderService : Service() {
    private val smsSender: ISmsSender by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}