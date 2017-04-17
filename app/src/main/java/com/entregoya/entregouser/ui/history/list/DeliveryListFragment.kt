package com.entregoya.entregouser.ui.history.list

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.delivery.EntregoDeliveryStatuses
import com.entregoya.entregouser.mvp.view.BaseMvpFragment
import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.DeliveryConfirmationFragment
import com.entregoya.entregouser.ui.delivery.escort.root.EscortActivity
import com.entregoya.entregouser.ui.history.details.DetailsDeliveryActivity
import com.entregoya.entregouser.ui.history.list.model.DeliveryHistoryAdapter
import com.entregoya.entregouser.ui.history.list.model.DeliveryListType
import kotlinx.android.synthetic.main.fragment_delivery_list.*
import kotlinx.android.synthetic.main.include_empty_delivery_list.*


class DeliveryListFragment : BaseMvpFragment<DeliveryListContract.View, DeliveryListContract.Presenter>(), DeliveryListContract.View {


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
        if (delivery_list_recycler.adapter == null)
            mPresenter.requestUpdate()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_delivery_list, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val extraType = arguments.getString(ARG_TYPE)
        mPresenter.setupType(DeliveryListType.valueOf(extraType))
        setupLayouts()
    }

    fun setupLayouts() {
        delivery_list_swipe.setOnRefreshListener { mPresenter.requestUpdate() }
        val colorAccent = ContextCompat.getColor(activity, R.color.colorAccent)
        val colorDarkBlue = ContextCompat.getColor(activity, R.color.colorDarkBlue)
        delivery_list_swipe.setColorSchemeColors(colorAccent, colorDarkBlue)
        delivery_list_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        delivery_empty_view_label.setOnClickListener { NavUtils.navigateUpFromSameTask(activity) }
    }

    override fun showEmptyView() {
        hideList()
        delivery_list_empty_view.visibility = View.VISIBLE
    }

    override fun hideList() {
        delivery_list_swipe.visibility = View.GONE
    }

    override fun hideEmptyView() {
        delivery_list_empty_view.visibility = View.GONE
    }

    override fun showProgress() {
        delivery_list_swipe.isRefreshing = true
    }

    override fun hideProgress() {
        delivery_list_swipe.isRefreshing = false
    }

    override fun showList(resultList: List<EntregoDeliveryPreview>) {
        hideEmptyView()
        delivery_list_swipe.visibility = View.VISIBLE
        delivery_list_recycler.adapter = DeliveryHistoryAdapter(resultList, mClickItemListener)
    }


    val mClickItemListener = object : DeliveryHistoryAdapter.ClickItemListener {
        override fun onItemClicked(delivery: EntregoDeliveryPreview) {
            when (delivery.status) {

                EntregoDeliveryStatuses.CONFIRMATION -> {
                    val fragment = DeliveryConfirmationFragment.getInstance(delivery)
                    activity.fragmentManager
                            .beginTransaction()
                            .replace(R.id.history_delivery_confirmation_container, fragment)
                            .addToBackStack(DeliveryConfirmationFragment.TAG)
                            .commit()
                }
                EntregoDeliveryStatuses.ASSIGNED,
                EntregoDeliveryStatuses.PENDING -> {
                    val intent = EscortActivity.getIntent(activity, delivery)
                    startActivity(intent)
                }
                else -> {
                    val intent = DetailsDeliveryActivity.getIntent(activity, delivery)
                    startActivity(intent)
                }
            }
        }

    }


}