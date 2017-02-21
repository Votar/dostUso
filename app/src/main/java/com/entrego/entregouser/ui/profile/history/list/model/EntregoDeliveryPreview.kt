package com.entrego.entregouser.ui.profile.history.list.model

import com.entrego.entregouser.entity.common.EntregoPriceEntity


class EntregoDeliveryPreview(val ulrPic: String,
                             val finishedTime: String,
                             val status: String,
                             val price: EntregoPriceEntity) {
}