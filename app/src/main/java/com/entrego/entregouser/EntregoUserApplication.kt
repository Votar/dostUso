package com.entrego.entregouser

import android.app.Application
import com.entrego.entregouser.storage.Storage
import com.entrego.entregouser.util.DEBUG
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class EntregoUserApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DEBUG = true
        Storage.init(applicationContext)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}