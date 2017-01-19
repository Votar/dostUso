package com.entrego.entregouser.storage

import com.entrego.entregouser.storage.preferences.PreferencesManager

object EntregoStorage {
    val token: String by lazy { PreferencesManager.getTokenOrEmpty() }
}