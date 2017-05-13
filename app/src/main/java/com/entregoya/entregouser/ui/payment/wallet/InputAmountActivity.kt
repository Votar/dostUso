package com.entregoya.entregouser.ui.payment.wallet

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.entregoya.entregouser.R
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.ui.payment.card.list.CardListFragment
import com.entregoya.entregouser.ui.payment.card.list.model.CardListAdapter
import com.entregoya.entregouser.ui.payment.wallet.model.AddMoneyWalletRequest
import com.entregoya.entregouser.ui.splash.model.GetProfileRequest
import com.entregoya.entregouser.util.loading
import com.entregoya.entregouser.util.showSnack
import com.entregoya.entregouser.util.showSnackError
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity
import kotlinx.android.synthetic.main.activity_input_amount.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class InputAmountActivity : AppCompatActivity() {

    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, InputAmountActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_amount)
        showCardFragment()
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        input_amount_btn.setOnClickListener { performResult() }
    }

    fun performResult() {
        val amount = input_amount_edit.text.toString()
        try {
            val amountFloat = amount.toFloat()
            if (mSelectedCard == null) {
                activity_input_amount_wallet.showSnackError(getString(R.string.error_should_select_card))
                return
            } else {
                showProgress()
                AddMoneyWalletRequest().executeAsync(
                        EntregoStorage.getTokenOrEmpty(),
                        amountFloat,
                        mSelectedCard!!.token,
                        mAddMoneyResponseListener
                )

            }

        } catch (ex: NumberFormatException) {
            activity_input_amount_wallet.showSnackError(getString(R.string.error_invalid_value))
        }
    }

    private fun showCardFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.input_amount_card_container, CardListFragment.newInstance(mCardItemClickedListener))
                .commit()
    }

    var mSelectedCard: EntregoCreditCardEntity? = null

    val mCardItemClickedListener = object : CardListAdapter.OnItemClicked {
        override fun onClick(card: EntregoCreditCardEntity) {
            mSelectedCard = card
        }
    }

    val mAddMoneyResponseListener = object : AddMoneyWalletRequest.AddMoneyWalletRequestListener {
        override fun onFailureAddMoneyWalletRequest(code: Int?, message: String?) {
            hideProgress()
            activity_input_amount_wallet.showSnackError(message)
        }

        override fun onSuccessAddMoneyWalletRequest() {
            hideProgress()
            activity_input_amount_wallet.showSnack(getString(R.string.success_added))
            updateProfile()
        }

    }

    private fun updateProfile() {
        val token = EntregoStorage.getTokenOrEmpty()
        GetProfileRequest().requestAsync(token, object : GetProfileRequest.ResponseListener {
            override fun onSuccessResponse(profileJson: CustomerProfileModel) {
                EntregoStorage.saveProfile(profileJson)
            }

            override fun onFailureResponse(code: Int?, message: String?) {
            }
        })
    }

    var mProgress: ProgressDialog? = null
    fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    fun hideProgress() {
        mProgress?.dismiss()
    }
}
