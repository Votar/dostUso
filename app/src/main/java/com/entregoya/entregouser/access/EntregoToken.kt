package com.entregoya.entregouser.access

import android.content.Context
import com.entregoya.entregouser.storage.preferences.PreferencesManager

object EntregoToken : BaseAccess<String>() {
    override fun init(context: Context) {
        content = PreferencesManager.getTokenOrEmpty()
    }
}