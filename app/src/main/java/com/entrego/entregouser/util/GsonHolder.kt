
package com.entrego.entregouser.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHolder {
    val instance : Gson = GsonBuilder().create()

}