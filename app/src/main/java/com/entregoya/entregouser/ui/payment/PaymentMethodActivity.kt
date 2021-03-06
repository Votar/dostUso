package com.entregoya.entregouser.ui.payment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.common.PaymentMethodEntity
import com.entregoya.entregouser.mvp.view.BaseMvpActivity
import com.entregoya.entregouser.ui.payment.card.add.AddCardActivity
import com.entregoya.entregouser.ui.payment.model.PaymentMethodAdapter
import com.entregoya.entregouser.ui.payment.wallet.InputAmountActivity
import com.entregoya.entregouser.util.loading
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity
import kotlinx.android.synthetic.main.activity_payment_method.*
import kotlinx.android.synthetic.main.navigation_payment_toolbar.*
import java.util.*

class PaymentMethodActivity : BaseMvpActivity<PaymentMethodContract.View, PaymentMethodContract.Presenter>(),
        PaymentMethodContract.View {
    companion object {


        fun getIntent(ctx: Context): Intent = Intent(ctx, PaymentMethodActivity::class.java)
    }

    override var mPresenter: PaymentMethodContract.Presenter = PaymentMethodPresenter()

    val mPaymentClickListener = object : PaymentMethodAdapter.OnPaymentClickListener {
        override fun onClick(method: PaymentMethodEntity) {
            mPresenter.setupLastPaymentMethod(method)
        }
    }

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
        mPresenter.loadPaymentMethod()
        mPresenter.requestCardList()
        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        val colorDarkBlue = ContextCompat.getColor(this, R.color.colorDarkBlue)
        payment_method_swiperefresh.setColorSchemeColors(colorAccent, colorDarkBlue)
        payment_method_swiperefresh.setOnRefreshListener { mPresenter.requestCardList() }
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
        startActivity(InputAmountActivity.getIntent(this))
//        getRootView().showSnack(getString(R.string.text_in_develop))
    }

    override fun showCardList(list: List<EntregoCreditCardEntity>) {
        (payment_method_recycler.adapter as PaymentMethodAdapter).addCards(list)
    }

    override fun loadDatasetPaymentMethods(list: LinkedList<Pair<PaymentMethodEntity, Boolean>>) {
        payment_method_recycler.adapter = PaymentMethodAdapter(list, mPaymentClickListener)
    }

    var mProgress: ProgressDialog? = null

    override fun hideProgress() {
        mProgress?.dismiss()
    }

    override fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    override fun getRootView(): View = activity_payment_method

    override fun showEditCardMenu() {
        edit_payment_menu.visibility = View.VISIBLE
        edit_payment_menu.setOnClickListener { mPresenter.deletePayment() }
    }

    override fun showDefaultMenu() {
        edit_payment_menu.visibility = View.GONE
    }

    override fun showCardProgress(){
        payment_method_swiperefresh.visibility = View.VISIBLE
        payment_method_swiperefresh.isRefreshing = true
    }

    override fun hideCardProgress() {
        payment_method_swiperefresh.visibility = View.GONE
        payment_method_swiperefresh.isRefreshing = false
    }
}

