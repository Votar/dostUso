package com.entrego.entregouser

import android.content.Intent
import android.support.multidex.MultiDexApplication
import com.entrego.entregouser.event.LogoutEvent
import com.entrego.entregouser.storage.Storage
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.auth.AuthActivity
import com.entrego.entregouser.util.DEBUG
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe

class EntregoUserApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        DEBUG = true
        Storage.init(applicationContext)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }


}