package com.entrego.entregouser.web.model.response.card

import com.entrego.entregouser.web.model.response.BaseEntregoResponse


class CardListResponse(code: Int?, message: String?,
                       val payload: Array<EntregoCreditCardEntity>) : BaseEntregoResponse(code, message)