package com.entrego.entregouser.storage.preferences


interface PreferencesContract {
    fun saveHomeAddress(address: String)
    fun getHomeAddressOrEmpty(): String
    fun saveWorkAddress(address: String)
    fun getWorkAddressOrEmpty(): String
}