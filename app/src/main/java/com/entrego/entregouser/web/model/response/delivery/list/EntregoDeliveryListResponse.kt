package com.entrego.entregouser.web.model.response.delivery.list

import com.entrego.entregouser.entity.back.EntregoDeliveryPreview
import com.entrego.entregouser.web.model.response.BaseEntregoResponse


class EntregoDeliveryListResponse(code:Int,
                                  message:String,
                                  val payload:Array<EntregoDeliveryPreview>
                                  ) :BaseEntregoResponse(code, message)