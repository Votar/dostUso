package com.entrego.entregouser

import android.app.Application
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.preferences.EntregoStorageContract
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.util.DEBUG
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class EntregoUserApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        PreferencesManager.init(applicationContext)
        DEBUG = true
    }
}