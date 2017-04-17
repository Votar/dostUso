package com.entregoya.entregouser.entity.common

import com.entregoya.entregouser.web.model.request.delivery.PaymentMethodBody
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity

class PaymentMethodEntity(val type: PaymentMethodType) {
    var card: EntregoCreditCardEntity? = null

    constructor(type: PaymentMethodType, card: EntregoCreditCardEntity) : this(type) {
        this.card = card
    }

    fun toPaymentBody(): PaymentMethodBody = PaymentMethodBody(type, card?.token, card?.mask)
}