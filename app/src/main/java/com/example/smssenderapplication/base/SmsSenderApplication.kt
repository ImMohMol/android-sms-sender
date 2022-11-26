package com.example.smssenderapplication.base

import android.app.Application
import com.example.smssenderapplication.util.SnackBarCreator
import com.example.smssenderapplication.util.interfaces.ISnackBarCreator
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class SmsSenderApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        val myModules = module {
            factory<ISnackBarCreator> { SnackBarCreator() }
        }

        startKoin {
            androidContext(this@SmsSenderApplication)
            modules(myModules)
        }
    }
}