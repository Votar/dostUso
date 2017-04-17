package com.entregoya.entregouser.ui.delivery.escort.cancel

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.entregoya.entregouser.R
import com.entregoya.entregouser.ui.delivery.escort.cancel.presenter.CancelDeliveryPresenter
import com.entregoya.entregouser.ui.delivery.escort.cancel.presenter.ICancelDeliveryPresenter
import com.entregoya.entregouser.ui.delivery.escort.cancel.view.ICancelDeliveryView
import com.entregoya.entregouser.util.loading
import kotlinx.android.synthetic.main.activity_cancel_delivery.*
import kotlinx.android.synthetic.main.navigation_toolbar.*


class CancelDeliveryActivity : AppCompatActivity(), ICancelDeliveryView {

    companion object {
        const val KEY_DELIVERY = "ext_k_delivey_id"
        fun getIntent(ctx: Context, deliveryId: Long): Intent {
            val intent = Intent(ctx, CancelDeliveryActivity::class.java)
            intent.putExtra(KEY_DELIVERY, deliveryId)
            return intent
        }
    }

    val mPresenter: ICancelDeliveryPresenter = CancelDeliveryPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_delivery)
        mPresenter.onCreate(this)
        setupLayouts()
        deserializeIntent()
    }

    fun deserializeIntent() {
        if (intent.hasExtra(KEY_DELIVERY)) {
            setupAdapter(intent.getLongExtra(KEY_DELIVERY, 0))
        } else throw IllegalStateException("No delivery id in intent")

    }


    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        cancel_delivery_recycler_reasons.addItemDecoration(dividerItemDecoration)
        cancel_delivery_recycler_reasons.layoutManager = layoutManager
    }

    fun setupAdapter(deliveryId: Long) {
        val reasons = resources.getStringArray(R.array.cancel_reasons).toList()
        mPresenter.setupRecyclerView(cancel_delivery_recycler_reasons, deliveryId, reasons)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    var loadingDialog: ProgressDialog? = null
    override fun onShowProgress() {
        loadingDialog = ProgressDialog(this)
        loadingDialog?.loading()
    }

    override fun onHideProgress() {
        loadingDialog?.dismiss()
    }

    override fun showMessage(message: String?) {

    }

    override fun getActivityContext(): Context {
        return this
    }

    override fun showSuccessScreen() {
        startActivity(Intent(this, SuccessCancelActivity::class.java))
    }


}
