package com.entrego.entregouser

import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.util.DEBUG
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import io.fabric.sdk.android.Fabric

class EntregoUserApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        DEBUG = true
        EntregoStorage.init(applicationContext)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        Fabric.with(this, Crashlytics())
    }


}