package com.entregoya.entregouser.web.model.response.card

class EntregoCreditCardEntity(val expire: CardExpireEntity,
                              val token: String,
                              val name: String,
                              val mask: String)