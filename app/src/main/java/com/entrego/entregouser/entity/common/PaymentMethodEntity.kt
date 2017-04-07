package com.entrego.entregouser.entity.common

import com.entrego.entregouser.web.model.response.card.EntregoCreditCardEntity

class PaymentMethodEntity(val type: PaymentMethodType) {
    var card: EntregoCreditCardEntity? = null

    constructor(type: PaymentMethodType, card: EntregoCreditCardEntity) : this(type) {
        this.card = card
    }
}