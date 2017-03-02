package com.entrego.entregouser.ui.profile.history.list

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpFragment
import com.entrego.entregouser.ui.profile.history.details.DetailsDeliveryActivity
import com.entrego.entregouser.ui.profile.history.list.model.DeliveryHistoryAdapter
import com.entrego.entregouser.ui.profile.history.list.model.DeliveryListType
import com.entrego.entregouser.ui.profile.history.list.model.EntregoDeliveryPreview
import com.entrego.entregouser.util.showSnack
import kotlinx.android.synthetic.main.fragment_delivery_list.*


class DeliveryListFragment private constructor() : BaseMvpFragment<DeliveryListContract.View, DeliveryListContract.Presenter>(), DeliveryListContract.View {


    override var mPresenter: DeliveryListContract.Presenter = DeliveryListPresenter()

    companion object {
        private const val ARG_TYPE = "ext_k_list_type"
        fun getCurrentDeliveryList(): DeliveryListFragment {
            val fragment = DeliveryListFragment()
            fragment.arguments = buildArguments(DeliveryListType.CURRENT)
            return fragment
        }

        fun getHistoryDeliveryList(): DeliveryListFragment {
            val fragment = DeliveryListFragment()
            fragment.arguments = buildArguments(DeliveryListType.HISTORY)
            return fragment
        }

        private fun buildArguments(type: DeliveryListType): Bundle {
            val args = Bundle()
            args.putString(ARG_TYPE, type.toString())
            return args
        }
    }

    override fun onStart() {
        super.onStart()
        val extraType = arguments.getString(ARG_TYPE)
        mPresenter.setupType(DeliveryListType.valueOf(extraType))
        setupLayouts()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_delivery_list, container, false)
        return view
    }

    fun setupLayouts() {
        delivery_list_swipe.setOnRefreshListener { mPresenter.requestUpdate() }
        val colorAccent = ContextCompat.getColor(activity, R.color.colorAccent)
        val colorDarkBlue = ContextCompat.getColor(activity, R.color.colorDarkBlue)
        delivery_list_swipe.setColorSchemeColors(colorAccent, colorDarkBlue)
        delivery_list_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

    }

    override fun showProgress() {
        delivery_list_swipe.isRefreshing = true
    }

    override fun hideProgress() {
        delivery_list_swipe.isRefreshing = false
    }

    override fun showList(resultList: List<EntregoDeliveryPreview>) {
        delivery_list_recycler.adapter = DeliveryHistoryAdapter(resultList, mClickItemListener)
    }


    val mClickItemListener = object : DeliveryHistoryAdapter.ClickItemListener {
        override fun onItemClicked(delivery: EntregoDeliveryPreview) {
            view?.showSnack("Clicked with price: " + delivery.price.toView())
            activity.startActivityFromFragment(this@DeliveryListFragment,
                    Intent(activity, DetailsDeliveryActivity::class.java),
                    0)
        }

    }


}