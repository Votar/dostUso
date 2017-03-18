package com.entrego.entregouser.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.history.list.DeliveryListFragment
import com.entrego.entregouser.ui.history.model.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_history_deliverys.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class HistoryDeliveriesActivity : BaseMvpActivity<HistoryDeliveryContract.View, HistoryDeliveryContract.Presenter>(),
        HistoryDeliveryContract.View {

    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, HistoryDeliveriesActivity::class.java)
    }

    override var mPresenter: HistoryDeliveryContract.Presenter = HistoryDeliveryPresenter()
    override fun getRootView(): View = activity_history_deliverys

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_deliverys)
        setupLayouts()
    }

    override fun onStart() {
        super.onStart()
    }

    fun setupLayouts() {
        setupListeners()
        setupViewPager()
    }

    fun setupListeners() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }


    fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DeliveryListFragment.getCurrentDeliveryList(), getString(R.string.tab_on_the_way))
        adapter.addFragment(DeliveryListFragment.getHistoryDeliveryList(), getString(R.string.tab_history))
        history_delivery_viewpager.adapter = adapter
        history_delivery_tablayout.setupWithViewPager(history_delivery_viewpager)
    }

}
