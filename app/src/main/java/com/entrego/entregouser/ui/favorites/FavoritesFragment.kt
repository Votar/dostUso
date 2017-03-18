package com.entrego.entregouser.ui.favorites

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.entrego.entregouser.R
import com.entrego.entregouser.mvp.view.BaseMvpFragment
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.realm.models.RealmAddressModel
import com.entrego.entregouser.ui.favorites.model.FavoritesAddressAdapter
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : BaseMvpFragment<FavoritesContract.View, FavoritesContract.Presenter>(),
        FavoritesContract.View {


    override var mPresenter: FavoritesContract.Presenter = FavoritesPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_favorites, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        setupLayouts()
        mPresenter.requestFavorites()
    }

    fun setupLayouts() {
        favorites_recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        favorites_recycler.addItemDecoration(dividerItemDecoration)
        favorites_recycler.adapter = FavoritesAddressAdapter(mItemClickListener)
        favorites_swipe_refresh.isEnabled = false
        favorites_value_home.text = EntregoStorage.getHomeAddressOrEmpty()
        favorites_value_work.text = EntregoStorage.getWorkAddressOrEmpty()
        favorites_home_rl.setOnClickListener {
            mPresenter.selectedHomeAddress(favorites_value_home.text.toString())
        }
        favorites_home_rl.setOnLongClickListener {
            showAutoCompleteForFavorite(FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_HOME)
            false
        }
        favorites_work_rl.setOnClickListener {
            mPresenter.selectedWorkAddress(favorites_value_work.text.toString())
        }
        favorites_work_rl.setOnLongClickListener {
            showAutoCompleteForFavorite(FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_WORK)
            false
        }
        favorites_add_fav.setOnClickListener {
            mPresenter.requestAddFavorites()
        }

        setUpItemTouchHelper()
        setUpAnimationDecoratorHelper()

    }


    val mItemClickListener = object : FavoritesAddressAdapter.OnItemClicked {
        override fun onClick(address: CharSequence) {
            (activity as? FavoritesController)?.selectedAddress(address.toString())
        }

    }

    override fun setupHomeAddress(address: String) {
        favorites_value_home.text = address
    }

    override fun setupWorkAddress(address: String) {
        favorites_value_work.text = address
    }

    override fun setupFavorites(list: List<RealmAddressModel>) {
        (favorites_recycler.adapter as? FavoritesAddressAdapter)
                ?.swapDataset(list)
    }

    override fun showAddFavorites() {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle(R.string.text_add_favorites_title)
        alert.setCancelable(false)
        val inflater = getLayoutInflater(arguments)
        val alertLayout = inflater.inflate(R.layout.fragment_add_favorite_place, null)
        val editTitle = alertLayout.findViewById(R.id.add_favorite_edit_place_title) as EditText
        alert.setView(alertLayout)

        alert.setNegativeButton(R.string.text_dismiss, { dialogInterface: DialogInterface, i: Int -> })
        alert.setPositiveButton(R.string.text_add, { dialogInterface: DialogInterface, i: Int ->
            run {
                mPresenter.addNameFavoritePlace(editTitle.text.toString())
            }
        })
        alert.show()
    }

    override fun selectedAddress(address: String) {
        (activity as? FavoritesController)?.selectedAddress(address)
    }

    override fun showProgress() {
        favorites_swipe_refresh.isEnabled = true
        favorites_swipe_refresh.isRefreshing = true
    }

    override fun hideProgress() {
        favorites_swipe_refresh.isRefreshing = false
        favorites_swipe_refresh.isEnabled = false
    }


    override fun showAutoCompleteForFavorite(requestCode: FavoritesContract.RequestCodes) {
        val filter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build()
        val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                .setFilter(filter)
                .build(activity)
        startActivityForResult(intent, requestCode.value)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check that the result was from the autocomplete widget.
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_FAVORITES.value -> data?.let {
                    val place = PlaceAutocomplete.getPlace(activity, data)
                    mPresenter.addAddressFavoritePlace(place.address.toString())
                }
                FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_HOME.value -> data?.let {
                    val place = PlaceAutocomplete.getPlace(activity, data)
                    favorites_value_home.text = place.address
                    EntregoStorage.saveHomeAddress(place.address.toString())
                }

                FavoritesContract.RequestCodes.REQUEST_CODE_AUTOCOMPLETE_WORK.value -> data?.let {
                    val place = PlaceAutocomplete.getPlace(activity, data)
                    favorites_value_work.text = place.address
                    EntregoStorage.saveWorkAddress(place.address.toString())
                }
            }
        }
    }


    private fun setUpItemTouchHelper() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


            var background: Drawable? = null
            var xMark: Drawable? = null
            var xMarkMargin: Int = 0
            var initiated: Boolean = false

            private fun init() {
                background = ColorDrawable(Color.RED)
                xMark = ContextCompat.getDrawable(activity, R.drawable.ic_delete_forever_black_24dp)
                xMark?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                xMarkMargin = activity.resources.getDimension(R.dimen.default_screen_padding).toInt()
                initiated = true
            }
            // not important, we don't want drag & drop

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val adapter = favorites_recycler.adapter as FavoritesAddressAdapter
                adapter.remove(swipedPosition)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.adapterPosition == -1) {
                    // not interested in those
                    return
                }
                if (!initiated) {
                    init()
                }
                // draw red background
                background?.apply {
                    setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    draw(c)
                }
                // draw x mark

                xMark?.apply {
                    val itemHeight = itemView.bottom - itemView.top
                    val intrinsicWidth = intrinsicWidth
                    val intrinsicHeight = getIntrinsicWidth()

                    val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
                    val xMarkRight = itemView.right - xMarkMargin
                    val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                    val xMarkBottom = xMarkTop + intrinsicHeight
                    setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)

                    draw(c)
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
        val mItemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        mItemTouchHelper.attachToRecyclerView(favorites_recycler)
    }

    private fun setUpAnimationDecoratorHelper() {
        favorites_recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            // we want to cache this and not allocate anything repeatedly in the onDraw method
            var background: Drawable? = null
            var initiated: Boolean = false

            private fun init() {
                background = ColorDrawable(Color.RED)
                initiated = true
            }

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

                if (!initiated) {
                    init()
                }

                // only if animation is in progress
                if (parent.itemAnimator.isRunning) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    var lastViewComingDown: View? = null
                    var firstViewComingUp: View? = null

                    // this is fixed
                    val left = 0
                    val right = parent.width

                    // this we need to find out
                    var top = 0
                    var bottom = 0

                    // find relevant translating views
                    val childCount = parent.layoutManager.childCount
                    for (i in 0..childCount - 1) {
                        val child = parent.layoutManager.getChildAt(i)
                        if (child.translationY < 0) {
                            // view is coming down
                            lastViewComingDown = child
                        } else if (child.translationY > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                        bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                        bottom = lastViewComingDown.bottom
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.top
                        bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                    }
                    background?.apply {
                        setBounds(left, top, right, bottom)
                        draw(c)
                    }
                }
                super.onDraw(c, parent, state)
            }
        })
    }
}