package com.entrego.entregouser.util

import android.content.Context


private val KEY_START_COUNT = "pref_k_start_count"
val STORAGE_NAME = "entrego_storage_user"

fun Context.isFirstStart(): Boolean =
        this.getSharedPreferences(com.entrego.entregouser.util.STORAGE_NAME, android.content.Context.MODE_PRIVATE).getBoolean(KEY_START_COUNT, true)

fun Context.disableFirstStart(){
    this.getSharedPreferences(com.entrego.entregouser.util.STORAGE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_START_COUNT, false)
            .apply()

}