package com.entrego.entregouser.access

import android.content.Context
import com.entrego.entregouser.storage.preferences.PreferencesManager

object EntregoToken : BaseAccess<String>() {
    override fun init(context: Context) {
        content = PreferencesManager.getTokenOrEmpty()
    }
}