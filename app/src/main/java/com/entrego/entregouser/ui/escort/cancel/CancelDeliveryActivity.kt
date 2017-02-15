package com.entrego.entregouser.ui.escort.cancel

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.escort.cancel.presenter.CancelDeliveryPresenter
import com.entrego.entregouser.ui.escort.cancel.presenter.ICancelDeliveryPresenter
import com.entrego.entregouser.ui.escort.cancel.view.ICancelDeliveryView
import com.entrego.entregouser.util.loading
import kotlinx.android.synthetic.main.activity_cancel_delivery.*
import kotlinx.android.synthetic.main.navigation_toolbar.*


class CancelDeliveryActivity : AppCompatActivity(), ICancelDeliveryView {

    val mPresenter: ICancelDeliveryPresenter = CancelDeliveryPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_delivery)
        mPresenter.onCreate(this)
        setupLayouts()
        setupAdapter()
    }

    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        cancel_delivery_recycler_reasons.addItemDecoration(dividerItemDecoration)
        cancel_delivery_recycler_reasons.layoutManager = layoutManager
    }

    fun setupAdapter() {
        val reasons = resources.getStringArray(R.array.cancel_reasons).toList()
        mPresenter.setupRecyclerView(cancel_delivery_recycler_reasons, reasons)
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
