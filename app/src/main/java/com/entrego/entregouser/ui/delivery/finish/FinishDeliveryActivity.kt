package com.entrego.entregouser.ui.delivery.finish

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.delivery.create.RootActivity
import com.entrego.entregouser.util.ui.EntregoCancelableProgressDialog
import com.entrego.entregouser.util.ui.EntregoProgressDialog
import kotlinx.android.synthetic.main.activity_finish_delivery.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class FinishDeliveryActivity : FinishDeliveryContract.View,
        BaseMvpActivity<FinishDeliveryContract.View,
                FinishDeliveryContract.Presenter>() {

    override var mPresenter: FinishDeliveryContract.Presenter = FinishDeliveryPresenter()
    var mProgress: EntregoProgressDialog? = null
    override fun getRootView(): View = activity_finish_delivery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_delivery)
        setupLayouts()
    }


    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        finish_send_btn.setOnClickListener(sendClickListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun setCommentError(message: String) {

    }

    override fun setCommentError(strResId: Int) {

    }

    override fun CleanErrorFields() {

    }

    val cancelAction = DialogInterface.OnClickListener { dialogInterface, i ->
        mPresenter.cancelLastRequest()
    }

    override fun showProgress() {
        mProgress = EntregoCancelableProgressDialog(this, cancelAction)
        mProgress?.show()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }

    val sendClickListener = View.OnClickListener {
        val message = finish_delivery_comment.text.toString()
        val rating = finish_delivery_mark.rating
        mPresenter.sendDeliveryComment(message, rating)
    }

    override fun onSuccessFinished() {
        Handler().postDelayed({
            val intent = Intent(this, RootActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }, 200)

    }

}
