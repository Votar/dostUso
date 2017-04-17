package com.entregoya.entregouser.util

import android.content.Context
import android.content.Intent
import com.entregoya.entregouser.web.api.EntregoApi


fun Context.shareRoute(code: String, deliveryId: Long) {
    val link = EntregoApi.BASE_URL + "share.xhtml?id={$deliveryId}&code={$code}"
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, link)
    sendIntent.type = "text/plain"
    startActivity(sendIntent)
}