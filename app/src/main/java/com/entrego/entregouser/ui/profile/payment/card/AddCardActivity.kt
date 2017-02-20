package com.entrego.entregouser.ui.profile.payment.card

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class AddCardActivity : BaseMvpActivity<AddCardContract.View, AddCardContract.Presenter>(),
        AddCardContract.View {

    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, AddCardActivity::class.java)
    }

    override var mPresenter: AddCardContract.Presenter = AddCardPresenter()

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
        add_card_scan_ll.setOnClickListener { }
        add_card_save_btn.setOnClickListener { }
    }

}
