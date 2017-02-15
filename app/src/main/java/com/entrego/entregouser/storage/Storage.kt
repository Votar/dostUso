package com.entrego.entregouser.storage

import android.content.Context
import com.entrego.entregouser.access.EntregoToken
import com.entrego.entregouser.storage.preferences.PreferencesManager

object Storage {
    fun init(context: Context) {
        PreferencesManager.init(context)
    }
}