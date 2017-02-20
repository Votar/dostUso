package com.entrego.entregouser.ui.profile.payment.wallet

import android.os.Bundle
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_add_money_to_wallet.*

class AddMoneyToWalletActivity : BaseMvpActivity<AddMoneyToWalletContract.View, AddMoneyToWalletContract.Presenter>(),
        AddMoneyToWalletContract.View {

    companion object{

    }

    override var mPresenter: AddMoneyToWalletContract.Presenter = AddMoneyToWalletPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_money_to_wallet)
    }
    

    override fun getRootView(): View = activity_add_money_to_wallet
}
