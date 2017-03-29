package com.entrego.entregouser.ui.payment.card

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.util.loading
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class AddCardActivity : BaseMvpActivity<AddCardContract.View, AddCardContract.Presenter>(),
        AddCardContract.View {


    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, AddCardActivity::class.java)
    }

    override var mPresenter: AddCardContract.Presenter = AddCardPresenter()
    var mProgress: ProgressDialog? = null

    override fun getRootView(): View = activity_add_card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        setupLayouts()
    }

    fun setupLayouts() {
        setupListeners()
    }

    fun setupListeners() {

        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        add_card_scan_ll.setOnClickListener { mPresenter.showScanCard() }
        add_card_save_btn.setOnClickListener { initSaveCard() }
    }

    private fun initSaveCard() {
        mPresenter.saveCard(
                "name",
                add_card_number.text.toString(),
                add_card_expiry_date.text.toString(),
                add_card_cvv.text.toString()
        )
    }

    override fun setupCardNumber(number: String) {
        add_card_number.setText(number)
    }

    override fun setupExpire(expiry: String) {
        add_card_expiry_date.setText(expiry)
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

    override fun setError(field: AddCardContract.FieldType, strResId: Int) {
        val errorText = getString(strResId)
        setError(field, errorText)
    }

    override fun setError(field: AddCardContract.FieldType, message: String) {
        when (field) {
            AddCardContract.FieldType.NUMBER -> TODO()
            AddCardContract.FieldType.EXPIRY -> TODO()
            AddCardContract.FieldType.CVV -> TODO()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.parseStartForResult(requestCode, resultCode, data)
    }

    override fun showScanCardActivity(requestCode: Int, intent: Intent?) {
        startActivityForResult(intent, requestCode)
    }
}

