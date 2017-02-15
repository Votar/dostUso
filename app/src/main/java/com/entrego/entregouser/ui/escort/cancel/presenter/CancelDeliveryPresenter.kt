package com.entrego.entregouser.ui.escort.cancel.presenter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import com.entrego.entregouser.R
import com.entrego.entregouser.access.EntregoToken
import com.entrego.entregouser.storage.preferences.PreferencesManager
import com.entrego.entregouser.ui.escort.cancel.ReasonsAdapter
import com.entrego.entregouser.ui.escort.cancel.model.CancelDelivery
import com.entrego.entregouser.ui.escort.cancel.view.ICancelDeliveryView


class CancelDeliveryPresenter : ICancelDeliveryPresenter {
    override fun setupRecyclerView(recycler: RecyclerView, reasons: List<String>) {
        recycler.adapter = ReasonsAdapter(reasons, mReasonClickedListener)
    }

    var mView: ICancelDeliveryView? = null
    override fun onCreate(view: ICancelDeliveryView) {
        mView = view
    }

    override fun onDestroy() {
        mView = null
    }

    val mReasonClickedListener = object : ReasonsAdapter.OnReasonClicked {
        override fun onClickedReason(reason: String) {
            val ctx = mView?.getActivityContext()
            if (ctx != null)
                cancelDeliveryDialog(ctx, reason)
        }
    }

    fun cancelDeliveryDialog(context: Context, reason: String) {

        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.cancel_delivery_message)
        builder.setPositiveButton(R.string.text_ok, { dialogInterface: DialogInterface, i: Int ->
            val token = PreferencesManager.getTokenOrEmpty()
            mView?.onShowProgress()

            val deliveryId = 0

            CancelDelivery.executeAsync(token, deliveryId, reason, object : CancelDelivery.CancelDeliveryListener {
                override fun onSuccessCancel() {
                    mView?.onHideProgress()
                    mView?.showSuccessScreen()
                }

                override fun onFailureCancel(message: String?, code: Int?) {
                    mView?.onHideProgress()
                    mView?.showMessage(message)
                }
            })
        })
        builder.setNegativeButton(R.string.text_cancel, { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        })
        builder.show()
    }


}