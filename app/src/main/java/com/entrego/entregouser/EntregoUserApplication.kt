package com.entrego.entregouser

import android.support.multidex.MultiDexApplication
import com.entrego.entregouser.storage.Storage
import com.entrego.entregouser.util.DEBUG
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class EntregoUserApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        DEBUG = true
        Storage.init(applicationContext)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }


}