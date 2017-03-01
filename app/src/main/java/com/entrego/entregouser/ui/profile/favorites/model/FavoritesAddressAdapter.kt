package com.entrego.entregouser.ui.profile.favorites.model

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.entrego.entregouser.R
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.realm.StorageContract
import com.entrego.entregouser.storage.realm.models.AddressType
import com.entrego.entregouser.storage.realm.models.RealmAddressModel
import java.util.*


class FavoritesAddressAdapter(val mFieldClickListener: OnItemClicked) : RecyclerView.Adapter<FavoritesAddressAdapter.ViewHolder>() {

    private val dataset = LinkedList<RealmAddressModel>()

    interface OnItemClicked {
        fun onClick(address: CharSequence)
    }

    class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
        var placeView: TextView? = null
        var addressView: TextView? = null
        var icon: ImageView? = null
    }

    fun swapDataset(newList: List<RealmAddressModel>) {
        dataset.clear()
        dataset.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_autocomplete_places, parent, false)

        // set the view's size, margins, paddings and layout parameters
        val vh = ViewHolder(v)

        vh.addressView = v.findViewById(R.id.item_autocomplete_address) as TextView
        vh.placeView = v.findViewById(R.id.item_autocomplete_place) as TextView
        vh.icon = v.findViewById(R.id.icon) as ImageView
        return vh
    }

    override fun getItemCount(): Int = dataset.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nextValue = dataset[position]
        holder.placeView?.text = nextValue.name
        holder.addressView?.text = nextValue.address
        var iconId = 0

        when (nextValue.type) {
            AddressType.FAVORITE.value -> {
                iconId = R.drawable.ic_star_yellow_36dp
                holder.placeView?.visibility = View.VISIBLE
            }
            AddressType.RECENT_SEARCH.value -> {
                iconId = R.drawable.ic_access_time_black_36dp
                holder.placeView?.visibility = View.GONE
            }
            else -> iconId = R.drawable.ic_status_ring_24dp
        }

        val icon = ContextCompat.getDrawable(holder.addressView?.context, iconId)
        holder.icon?.setImageDrawable(icon)

        holder.rootView.setOnClickListener { mFieldClickListener.onClick(nextValue.address) }
    }

    fun remove(position: Int) {
        val item = dataset.get(position)
        if (dataset.contains(item)) {
            dataset.removeAt(position)
            notifyItemRemoved(position)
            EntregoStorage.removeAddress(item)
        }
    }


}
