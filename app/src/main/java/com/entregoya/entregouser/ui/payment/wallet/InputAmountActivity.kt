package com.entregoya.entregouser.ui.payment.wallet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entregoya.entregouser.R
import com.entregoya.entregouser.ui.payment.wallet.card.WalletInputActivity
import com.entregoya.entregouser.util.showSnackError
import kotlinx.android.synthetic.main.activity_input_amount.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class InputAmountActivity : AppCompatActivity() {

    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, InputAmountActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_amount)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        input_amount_btn.setOnClickListener { performResult() }
    }

    fun performResult() {
        val amount = input_amount_edit.text.toString()
        try {
            val amountFloat = amount.toFloat()
            startActivity(WalletInputActivity.getIntent(this, amountFloat))
        } catch (ex: NumberFormatException) {
            activity_input_amount_wallet.showSnackError(getString(R.string.error_invalid_value))
        }
    }
}
