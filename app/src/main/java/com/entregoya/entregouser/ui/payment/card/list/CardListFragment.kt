package com.entregoya.entregouser.ui.payment.card.list

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.entregouser.R
import com.entregoya.entregouser.mvp.view.BaseMvpFragment
import com.entregoya.entregouser.ui.payment.card.list.model.CardListAdapter
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity
import kotlinx.android.synthetic.main.fragment_card_list.*


class CardListFragment : BaseMvpFragment<CardListContract.View, CardListContract.Presenter>(),
        CardListContract.View {

    companion object {
        fun newInstance(listener: CardListAdapter.OnItemClicked): CardListFragment {
            val fragment = CardListFragment()
            fragment.mItemClickListener = listener
            return fragment
        }
    }

    override var mPresenter: CardListContract.Presenter = CardListPresenter()
    var mItemClickListener: CardListAdapter.OnItemClicked? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_card_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayouts()
    }

    fun setupLayouts() {
        card_list_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        card_list_recycler.addItemDecoration(dividerItemDecoration)
        mItemClickListener?.let {
            card_list_recycler.adapter = CardListAdapter(it)
        }
        card_list_refresh.setOnRefreshListener { mPresenter.requestUpdate() }
        val colorAccent = ContextCompat.getColor(activity, R.color.colorAccent)
        val colorDarkBlue = ContextCompat.getColor(activity, R.color.colorDarkBlue)
        card_list_refresh.setColorSchemeColors(colorAccent, colorDarkBlue)
    }

    override fun onStart() {
        super.onStart()
        if (card_list_recycler.adapter.itemCount == 0)
            mPresenter.requestUpdate()

    }

    override fun setupCardList(resultList: List<EntregoCreditCardEntity>) {
        (card_list_recycler.adapter as CardListAdapter).swapDataset(resultList)
        val count = card_list_recycler.adapter.itemCount
        val heightItem = Math.round(resources.getDimension(R.dimen.payment_method_cell_height))
        val heightLayout = heightItem * count
        card_list_refresh.layoutParams.height = heightLayout
    }

    override fun showProgress() {
        card_list_refresh.isRefreshing = true
    }

    override fun hideProgress() {
        card_list_refresh.isRefreshing = false
    }

    override fun showEmptyView() {
        card_list_empty_rl.visibility = View.VISIBLE
        card_list_recycler.visibility = View.GONE
        card_list_refresh.visibility = View.GONE
    }

    override fun hideEmptyView() {
        card_list_empty_rl.visibility = View.GONE
        card_list_recycler.visibility = View.VISIBLE
        card_list_refresh.visibility = View.VISIBLE

    }

    fun dpToPx(dp: Int): Int {
        val displayMetrics = this.resources.displayMetrics
        return Math.round(dp * (displayMetrics.density / DisplayMetrics.DENSITY_DEFAULT))

    }


}