package com.entregoya.entregouser.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.route.EntregoPointBinding
import com.entregoya.entregouser.entity.route.EntregoRouteModel
import com.entregoya.entregouser.storage.preferences.PreferencesManager
import com.entregoya.entregouser.ui.intro.IntroActivity
import com.entregoya.entregouser.web.api.EntregoApi
import com.entregoya.entregouser.web.socket.SocketService

fun View.showSnack(message: String?) {
    val text: String
    if (message.isNullOrEmpty()) {
        text = this.context.getString(R.string.er_default_network_error)
    } else
        text = message!!

    val snackBar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    val sbMessageTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
    val primaryColor = ContextCompat.getColor(this.context, R.color.colorPrimary)
    sbMessageTextView.setTextColor(primaryColor)
    snackBar.show()
}

fun View.showSnackError(message: String?) {
    val text: String
    if (message.isNullOrEmpty()) {
        text = this.context.getString(R.string.er_default_network_error)
    } else
        text = message!!

    val snackBar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    val sbMessageTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
    val primaryColor = ContextCompat.getColor(this.context, R.color.colorTomato)
    sbMessageTextView.setTextColor(primaryColor)
    snackBar.show()
}


fun ProgressDialog.loading() {
    this.setTitle(this.context.getString(R.string.ui_progress_dialog_title))
    this.setMessage(this.context.getString(R.string.ui_loading))
    this.show()
}

fun ProgressDialog.loadingWithCancel(cancelAction: (dialog: DialogInterface, which: Int) -> Unit) {
    this.setTitle(this.context.getString(R.string.ui_progress_dialog_title))
    this.setMessage(this.context.getString(R.string.ui_loading))
    this.setButton(Dialog.BUTTON_NEGATIVE,
            this.context.getString(android.R.string.cancel)
            , cancelAction)
    this.show()
}

fun Context.logout() {
    stopService(Intent(this, SocketService::class.java))
    PreferencesManager.setToken("")
    val intent = IntroActivity.getIntent(this)
    startActivity(intent)
}

fun EntregoRouteModel.getStaticMapUrlWithWaypoints(): String {
    val line = path.line
    val staticPartUrl = "https://maps.googleapis.com/maps/api/staticmap?autoscale=1" +
            "&size=600x300" +
            "&maptype=roadmap" +
            "&format=png" +
            "&path=weight:3%7Ccolor:blue%7Cenc:$line" +
            "&visual_refresh=true"
    val urlBuilder = StringBuilder()
    urlBuilder.append(staticPartUrl)

    waypoints.forEachIndexed { i, point ->
        val coordinates = "" + waypoints[i].point.latitude +
                "," + waypoints[i].point.longitude
        val label = i + 1
        urlBuilder.append("&markers=size:mid%7Clabel:$label%7C$coordinates")
    }
    return urlBuilder.toString()
}

fun Array<EntregoPointBinding>.getStaticMapUrl(path: String?): String {
    val staticPartUrl = "https://maps.googleapis.com/maps/api/staticmap?autoscale=1" +
            "&size=600x300" +
            "&maptype=roadmap" +
            "&format=png" +
            "&path=weight:3%7Ccolor:blue%7Cenc:$path" +
            "&visual_refresh=true"
    val urlBuilder = StringBuilder()
    urlBuilder.append(staticPartUrl)

    this.forEachIndexed { i, point ->
        val coordinates = "" + this[i].point.latitude +
                "," + this[i].point.longitude
        val label = i + 1
        urlBuilder.append("&markers=size:mid%7Clabel:$label%7C$coordinates")
    }
    return urlBuilder.toString()
}

fun SwipeRefreshLayout.setDefaultColorSchema() {
    val colorAccent = ContextCompat.getColor(this.context, R.color.colorAccent)
    val colorDarkBlue = ContextCompat.getColor(this.context, R.color.colorDarkBlue)
    this.setColorSchemeColors(colorAccent, colorDarkBlue)
}

fun String.isValidLong(): Boolean {
    try {
        this.toLong()
    } catch (ex: java.lang.NumberFormatException) {
        return false
    }
    return true
}

fun String.isValidInt(): Boolean {
    try {
        this.toInt()
    } catch (ex: java.lang.NumberFormatException) {
        return false
    }
    return true
}

fun String.isValidShort(): Boolean {
    try {
        this.toShort()
    } catch (ex: java.lang.NumberFormatException) {
        return false
    }
    return true
}

fun ImageView.loadMessengerPicWithToken(messengerId: Long) {
    logd("Messenger url = " + EntregoApi.URL_MESSENGER_PIC + messengerId.toString())
    Glide.with(this.context)
            .load(EntregoApi.URL_MESSENGER_PIC + messengerId.toString())
            .error(R.drawable.ic_user_pic_holder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)
}

fun ImageView.loadCustomerPicWithToken() {
    Glide.with(this.context)
            .load(EntregoApi.URL_CUSTOMER_PIC)
            .placeholder(R.drawable.ic_user_pic_holder)
            .dontAnimate()
            .error(R.drawable.ic_user_pic_holder)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)
}




