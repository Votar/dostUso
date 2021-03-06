package com.entregoya.entregouser.util.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import com.entregoya.entregouser.R
import com.entregoya.entregouser.util.loadingWithCancel


class EntregoCancelableProgressDialog(ctx: Context, val cancelClickListener: DialogInterface.OnClickListener) : EntregoProgressDialog(ctx) {


    override fun show() {
        this.setButton(Dialog.BUTTON_NEGATIVE,
                this.context.getString(android.R.string.cancel)
                , cancelClickListener)
        super.show()
    }


}