package com.entrego.entregouser.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.entity.route.EntregoRouteModel
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.auth.AuthActivity

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
    PreferencesManager.setToken("")
    startActivity(AuthActivity.getIntent(this))
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


