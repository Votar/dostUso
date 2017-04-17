package com.entregoya.entregouser.web.model.request.delivery

import com.entregoya.entregouser.entity.common.PaymentMethodType


class PaymentMethodBody(val type: PaymentMethodType,
        //CARD TOKEN
                        val value: String?,
        //CARD MASK
                        val description: String?)