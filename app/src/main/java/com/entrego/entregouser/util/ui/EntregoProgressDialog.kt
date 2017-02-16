package com.entrego.entregouser.util.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import com.entrego.entregouser.R
import com.entrego.entregouser.util.loadingWithCancel


open class EntregoProgressDialog(ctx: Context) : ProgressDialog(ctx) {

    override fun show() {
        setTitle(context.getString(R.string.ui_progress_dialog_title))
        setMessage(context.getString(R.string.ui_loading))
        super.show()
    }
}