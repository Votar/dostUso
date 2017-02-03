package com.entrego.entregouser.entity.common

class EntregoPriceEntity(val amount: Float = 0f,
                         val currency: String ="$") {

    override fun toString(): String {
        return "EntregoPriceEntity(amount=$amount, currency='$currency')"
    }

    fun toView(): String {
        return "$currency $amount"
    }
}