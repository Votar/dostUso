package com.entregoya.entregouser.web.model.response.delivery.list

import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.web.model.response.BaseEntregoResponse


class EntregoDeliveryListResponse(code:Int,
                                  message:String,
                                  val payload:Array<EntregoDeliveryPreview>
                                  ) :BaseEntregoResponse(code, message)