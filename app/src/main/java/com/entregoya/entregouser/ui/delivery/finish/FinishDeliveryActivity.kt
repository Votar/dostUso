package com.entregoya.entregouser.ui.delivery.finish

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NavUtils
import android.view.View
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.common.EntregoMessengerView
import com.entregoya.entregouser.entity.common.EntregoPriceEntity
import com.entregoya.entregouser.mvp.view.BaseMvpActivity
import com.entregoya.entregouser.ui.delivery.create.RootActivity
import com.entregoya.entregouser.util.GsonHolder
import com.entregoya.entregouser.util.ui.EntregoCancelableProgressDialog
import com.entregoya.entregouser.util.ui.EntregoProgressDialog
import kotlinx.android.synthetic.main.activity_finish_delivery.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class FinishDeliveryActivity : FinishDeliveryContract.View,
        BaseMvpActivity<FinishDeliveryContract.View,
                FinishDeliveryContract.Presenter>() {

    companion object {
        private const val KEY_MESSENGER = "ext_k_messenger"
        private const val KEY_DELIVERY_ID = "ext_k_delivery_id"
        private const val KEY_ORDER_ID = "ext_k_order_id"
        private const val KEY_DELIVERY_SUM = "ext_k_delivery_sum"

        fun getIntent(ctx: Context,
                      deliveryId: Long,
                      orderId: Long,
                      sum: EntregoPriceEntity,
                      messenger: EntregoMessengerView) =
                Intent(ctx, FinishDeliveryActivity::class.java).apply {
                    putExtra(KEY_MESSENGER, GsonHolder.instance.toJson(messenger, EntregoMessengerView::class.java))
                    putExtra(KEY_DELIVERY_ID, deliveryId)
                    putExtra(KEY_ORDER_ID, orderId)
                    putExtra(KEY_DELIVERY_SUM, GsonHolder.instance.toJson(sum, EntregoPriceEntity::class.java))
                }
    }

    override var mPresenter: FinishDeliveryContract.Presenter = FinishDeliveryPresenter()
    var mProgress: EntregoProgressDialog? = null
    override fun getRootView(): View = activity_finish_delivery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_delivery)
        setupLayouts()
        deserializeIntent()
    }


    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        finish_send_btn.setOnClickListener(sendClickListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavUtils.navigateUpFromSameTask(this)
    }

    fun deserializeIntent() {
        val orderId: Long
        if (intent.hasExtra(KEY_MESSENGER)) {
            val jsonMessenger = intent.getStringExtra(KEY_MESSENGER)
            val messengerModel = GsonHolder.instance.fromJson(jsonMessenger, EntregoMessengerView::class.java)
            setupMessenger(messengerModel)
        } else throw IllegalStateException("No messenger information in intent")
        if (intent.hasExtra(KEY_DELIVERY_ID)) {
            val deliveryId = intent.getLongExtra(KEY_DELIVERY_ID, 0)
            mPresenter.setupDeliveryId(deliveryId)
        } else throw IllegalStateException("No deliveryId in intent")
        if (intent.hasExtra(KEY_DELIVERY_SUM)) {
            val priceJson = intent.getStringExtra(KEY_DELIVERY_SUM)
            val priceModel = GsonHolder.instance.fromJson(priceJson, EntregoPriceEntity::class.java)
            finish_total_sum.text = priceModel.toView()
        }
        if (intent.hasExtra(KEY_ORDER_ID)) {
            orderId = intent.getLongExtra(KEY_ORDER_ID, 0)
            mPresenter.setupOrderId(orderId)
        } else throw IllegalStateException("No orderId in intent")
    }

    fun setupMessenger(messenger: EntregoMessengerView) {
        finish_messenger_name.text = messenger.name
//        finish_messenger_rating.numStars = messenger.rating
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
        mPresenter.sendDeliveryComment(message, rating.toInt())
    }

    override fun onSuccessFinished() {
        Handler().postDelayed({
            val intent = Intent(this, RootActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }, 200)

    }

}
