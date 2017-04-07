package com.entrego.entregouser.ui.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.common.PaymentMethodEntity
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.payment.card.add.AddCardActivity
import com.entrego.entregouser.ui.payment.model.PaymentMethodAdapter
import com.entrego.entregouser.ui.payment.wallet.AddMoneyToWalletActivity
import com.entrego.entregouser.web.model.response.card.EntregoCreditCardEntity
import kotlinx.android.synthetic.main.activity_payment_method.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*

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
        setupListeners()
        payment_method_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        payment_method_recycler.addItemDecoration(dividerItemDecoration)
        mPresenter.requestCardList()
        mPresenter.loadPaymentMethod()
    }

    fun setupListeners() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        payment_method_add_card.setOnClickListener { showAddCardActivity() }
        payment_method_add_money_to_wallet.setOnClickListener { showAddMoneyToWallet() }
        payment_method_save_btn.setOnClickListener {
            val toSave = (payment_method_recycler.adapter as PaymentMethodAdapter).getCheckedPaymentMethod()
            mPresenter.savePaymentMethod(toSave)
        }
    }

    override fun showAddCardActivity() {
        startActivity(AddCardActivity.getIntent(this))
    }

    override fun showAddMoneyToWallet() {
        startActivity(AddMoneyToWalletActivity.getIntent(this))
    }

    override fun showCardList(list: List<EntregoCreditCardEntity>) {
        (payment_method_recycler.adapter as PaymentMethodAdapter).addCards(list)
    }

    override fun loadDatasetPaymentMethods(list: LinkedList<Pair<PaymentMethodEntity, Boolean>>) {
        payment_method_recycler.adapter = PaymentMethodAdapter(list)
    }
}

