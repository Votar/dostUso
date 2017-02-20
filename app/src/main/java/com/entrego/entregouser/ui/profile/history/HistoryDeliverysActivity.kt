package com.entrego.entregouser.ui.profile.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpActivity
import com.entrego.entregouser.ui.profile.history.model.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_history_deliverys.*

class HistoryDeliverysActivity : BaseMvpActivity<HistoryDeliveryContract.View, HistoryDeliveryContract.Presenter>(),
        HistoryDeliveryContract.View {

    companion object {
        fun getIntent(ctx: Context): Intent = Intent(ctx, HistoryDeliverysActivity::class.java)
    }

    override var mPresenter: HistoryDeliveryContract.Presenter = HistoryDeliveryPresenter()
    override fun getRootView(): View = activity_history_deliverys

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_deliverys)
    }

    fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(ProfileFragment(), getString(R.string.tab_on_the_way)
//        adapter.addFragment(VehicleFragment(), getString(R.string.tab_history)
//        viewPager.adapter = adapter
    }

}
