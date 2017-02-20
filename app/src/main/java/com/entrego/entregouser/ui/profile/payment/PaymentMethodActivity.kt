package com.entrego.entregouser.ui.profile.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.profile.payment.card.AddCardActivity
import kotlinx.android.synthetic.main.activity_payment_method.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class PaymentMethodActivity : BaseMvpActivity<PaymentMethodContract.View, PaymentMethodContract.Presenter>(),
        PaymentMethodContract.View {

    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, PaymentMethodActivity::class.java)
    }

    override var mPresenter: PaymentMethodContract.Presenter = PaymentMethodPresenter()
    override fun getRootView(): View = activity_payment_method

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)
        setupLayouts()
    }

    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        payment_method_add_card.setOnClickListener { showAddCardActivity() }
    }

    override fun showAddCardActivity() {
        startActivity(AddCardActivity.getIntent(this))
    }

    override fun showAddMoneyToWallet() {

    }
}
