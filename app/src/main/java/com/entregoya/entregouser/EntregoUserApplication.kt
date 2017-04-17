package com.entregoya.entregouser

import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.util.DEBUG
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