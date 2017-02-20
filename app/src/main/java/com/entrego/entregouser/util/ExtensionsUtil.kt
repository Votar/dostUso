package com.entrego.entregouser.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.entrego.entregouser.R
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
    val intent = android.content.Intent(this, AuthActivity::class.java)
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
}


