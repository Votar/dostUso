package com.entregoya.entregouser.ui.payment.card.add

import android.content.Intent
import com.entregoya.entregouser.R
import com.entregoya.entregouser.mvp.presenter.BaseMvpPresenter
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.ui.payment.card.add.model.AddCardRequest
import com.entregoya.entregouser.util.isValidInt
import com.entregoya.entregouser.util.isValidLong
import com.entregoya.entregouser.util.isValidShort
import com.entregoya.entregouser.web.api.ApiContract
import com.entregoya.entregouser.web.model.request.AddCardBody
import com.entregoya.entregouser.web.model.response.common.FieldError
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

        override fun onFailureAddCard(code: Int?, message: String?) {
            mView?.hideProgress()
            when (code) {
                ApiContract.RESPONSE_INVALID_TOKEN -> mView?.logout()
                else -> mView?.showError(message)
            }
        }

        override fun onValidationError(fields: Array<FieldError>) {
            mView?.enableInputLayoutsError()
            mView?.hideProgress()
            fields.forEach {
                val filed = AddCardContract.FieldType.fromString(it.field)
                mView?.setError(filed, it.message)
            }
        }
    }

    override fun saveCard(name: String, number: String, month: String, year: String, cvv: String) {
        mView?.disableInputLayoutsError()

        if (name.isEmpty()) {
            mView?.setError(AddCardContract.FieldType.NAME, R.string.error_required_field)
            return
        }
        if (number.isEmpty()) {
            mView?.setError(AddCardContract.FieldType.NUMBER, R.string.error_required_field)
            return
        }
        if (month.isEmpty()) {
            mView?.setError(AddCardContract.FieldType.MONTH, R.string.error_required_field)
            return
        }
        if (year.isEmpty()) {
            mView?.setError(AddCardContract.FieldType.YEAR, R.string.error_required_field)
            return
        }
        if (cvv.isEmpty()) {
            mView?.setError(AddCardContract.FieldType.CVV, R.string.error_required_field)
            return
        }

        if (number.isValidLong().not()) {
            mView?.setError(AddCardContract.FieldType.NUMBER, R.string.error_invalid_value)
            return
        }
        if (month.isValidInt().not()) {
            mView?.setError(AddCardContract.FieldType.MONTH, R.string.error_invalid_value)
            return
        }
        if (year.isValidInt().not()) {
            mView?.setError(AddCardContract.FieldType.YEAR, R.string.error_invalid_value)
            return
        }
        if (cvv.isValidShort().not()) {
            mView?.setError(AddCardContract.FieldType.CVV, R.string.error_invalid_value)
            return
        }


        val body = AddCardBody(name, number.toLong(), month.toInt(), year.toInt(), cvv.toShort())
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
                //TODO : refactor by required format
                mView?.setupExpire(
                        scanResult.expiryMonth.toString(),
                        scanResult.expiryYear.toString()
                )

                if (scanResult.cvv != null)
                    mView?.setupCvv(scanResult.cvv)

            }

        } else {
        }
    }
}