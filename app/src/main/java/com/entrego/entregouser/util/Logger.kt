package com.entrego.entregouser.util

import android.util.Log

var DEBUG: Boolean = false
fun Any.logd(tag: String ="DEBUG", message: String?) {
    if (DEBUG)
        Log.d(tag, message)
}