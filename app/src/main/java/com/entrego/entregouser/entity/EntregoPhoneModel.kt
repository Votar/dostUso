package com.entrego.entregouser.entity

data class EntregoPhoneModel(val code: String?,
                             val number: String?) {

    fun toView(): String = "$code$number"
    override fun toString(): String {
        return "EntregoPhoneModel(code=$code, number=$number)"
    }
}