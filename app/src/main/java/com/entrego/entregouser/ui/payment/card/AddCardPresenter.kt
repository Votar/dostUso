package com.entrego.entregouser.ui.payment.card

import android.content.Intent
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.ui.payment.card.model.AddCardRequest
import com.entrego.entregouser.web.model.request.AddCardBody
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard


class AddCardPresenter : BaseMvpPresenter<AddCardContract.View>(), AddCardContract.Presenter {

    companion object {
        val REQUEST_SCAN_CARD = 0x1122
    }

    val mToken = EntregoStorage.getTokenOrEmpty()

    val mAddCardListener = object : AddCardRequest.AddCardListener {
        override fun onSuccessAddCard() {
            mView?.hideProgress()
            mView?.showMessage(R.string.text_card_have_been_added)
        }

        override fun onFailureAddCard(message: String?, code: Int?) {
            //TODO: add error parsing
            mView?.hideProgress()
            mView?.showError(message)
        }
    }

    override fun saveCard(name: String, number: String, expiry: String, cvv: String) {
        //TODO : Add data validation
        val body = AddCardBody(name, number, expiry, cvv)
        mView?.showProgress()
        AddCardRequest().executeAsync(mToken, body, mAddCardListener)
    }

    override fun showScanCard() {
        val scanIntent = Intent(mView?.getAppContext(), CardIOActivity::class.java)

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true) // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true) // default: false

        mView?.showScanCardActivity(REQUEST_SCAN_CARD, scanIntent)
    }

    override fun parseStartForResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SCAN_CARD) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                val scanResult: CreditCard = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)
                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                mView?.setupCardNumber(scanResult.cardNumber)

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );
                if (scanResult.isExpiryValid) {
                    //TODO : refactor by required format
                    val expiry = scanResult.expiryMonth.toString() + "/" + scanResult.expiryYear
                    mView?.setupExpire(expiry)
                }

                if (scanResult.cvv != null) {
                    mView?.setupCvv(scanResult.cvv)
                }
            }

        } else {
        }
    }
}