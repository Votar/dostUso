package com.entregoya.entregouser.util.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import com.entregoya.entregouser.R
import com.entregoya.entregouser.util.loadingWithCancel


open class EntregoProgressDialog(ctx: Context) : ProgressDialog(ctx) {

    override fun show() {
        setTitle(context.getString(R.string.ui_progress_dialog_title))
        setMessage(context.getString(R.string.ui_loading))
        super.show()
    }
}