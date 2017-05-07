package com.entregoya.entregouser.ui.payment.wallet.card

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.NavUtils
import android.view.View
import com.entregoya.entregouser.R
import com.entregoya.entregouser.mvp.view.BaseMvpActivity
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.util.loading
import kotlinx.android.synthetic.main.activity_add_card_wallet.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class WalletInputActivity : BaseMvpActivity<WalletInputContract.View, WalletInputContract.Presenter>(),
        WalletInputContract.View {

    companion object {
        private val KEY_EXT_AMOUNT = "k_ext_amount"
        fun getIntent(ctx: Context, amount: Float): Intent {
            val intent = Intent(ctx, WalletInputActivity::class.java)
            intent.putExtra(KEY_EXT_AMOUNT, amount)
            return intent
        }
    }

    override var mPresenter: WalletInputContract.Presenter = WalletInputPresenter()
    var mProgress: ProgressDialog? = null


    override fun getRootView(): View = activity_add_card_wallet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card_wallet)
        setupLayouts()
    }

    fun setupLayouts() {
        setupListeners()
    }

    fun getInputLayouts(): Array<TextInputLayout> = arrayOf(
            add_card_holder_il,
            add_card_number_il,
            add_card_exp_month_il,
            add_card_exp_year_il,
            add_card_cvv_il
    )

    fun setupListeners() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        add_card_scan_ll.setOnClickListener { mPresenter.showScanCard() }
        add_card_save_btn.setOnClickListener { initSaveCard() }

        EntregoStorage.getProfile()?.apply {
            add_card_holder_name.setText(name)
            name?.apply { add_card_holder_name.setSelection(length) }
        }

    }

    private fun initSaveCard() {
        mPresenter.saveCard(
                add_card_holder_name.text.toString(),
                add_card_number.text.toString(),
                add_card_expire_month.text.toString(),
                add_card_expire_year.text.toString(),
                add_card_cvv.text.toString()
        )
    }

    override fun setupCardNumber(number: String) {
        add_card_number.setText(number)
    }

    override fun setupExpire(month: String, year: String) {
        add_card_expire_month.setText(month)
        add_card_expire_year.setText(year)
    }

    override fun setupCvv(cvv: String) {
        add_card_cvv.setText(cvv)
    }

    override fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }

    override fun setError(field: WalletInputContract.FieldType, strResId: Int) {
        val errorText = getString(strResId)
        setError(field, errorText)
    }

    override fun disableInputLayoutsError() {
        getInputLayouts().forEach {
            it.error = null
            it.isErrorEnabled = false
        }
    }

    override fun enableInputLayoutsError() {
        getInputLayouts().forEach { it.isErrorEnabled = true }
    }

    override fun setError(field: WalletInputContract.FieldType, message: String) {
        when (field) {
            WalletInputContract.FieldType.NUMBER -> {
                add_card_number_il.error = message
                add_card_number.requestFocus()
            }
            WalletInputContract.FieldType.CVV -> {
                add_card_cvv_il.error = message
                add_card_cvv.requestFocus()
            }
            WalletInputContract.FieldType.NAME -> {
                add_card_holder_il.error = message
                add_card_holder_name.requestFocus()
            }
            WalletInputContract.FieldType.MONTH -> {
                add_card_exp_month_il.error = message
                add_card_expire_month.requestFocus()
            }
            WalletInputContract.FieldType.YEAR -> {
                add_card_exp_year_il.error = message
                add_card_expire_year.requestFocus()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.parseStartForResult(requestCode, resultCode, data)
    }

    override fun showScanCardActivity(requestCode: Int, intent: Intent?) {
        startActivityForResult(intent, requestCode)
    }

    override fun logout() {
        this.logout()
    }

}

