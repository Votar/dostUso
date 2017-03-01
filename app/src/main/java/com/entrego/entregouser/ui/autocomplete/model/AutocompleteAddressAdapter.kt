package com.entrego.entregouser.ui.autocomplete.model

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.text.style.CharacterStyle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.ui.delivery.create.steps.address.mvp.model.WayPointsAdapter
import com.entrego.entregouser.ui.delivery.create.steps.address.mvp.view.FieldClickListener
import com.google.android.gms.location.places.AutocompletePrediction
import com.google.android.gms.location.places.AutocompletePredictionBuffer
import java.util.*

class AutocompleteAddressAdapter(val mFieldClickListener: OnItemClicked) : RecyclerView.Adapter<AutocompleteAddressAdapter.ViewHolder>() {

    private val dataset = LinkedList<AutocompletePrediction>()

    interface OnItemClicked {
        fun onClick(address: CharSequence)
    }

    class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
        var placeView: TextView? = null
        var addressView: TextView? = null
        var index: Int? = null
    }

    fun swapDataset(newList: List<AutocompletePrediction>) {
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
        return vh
    }

    override fun getItemCount(): Int = dataset.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nextValue = dataset[position]
        holder.placeView?.text = nextValue.getPrimaryText(null)
        holder.addressView?.text = nextValue.getSecondaryText(null)
        holder.rootView.setOnClickListener {
            mFieldClickListener.onClick(nextValue.getFullText(null))
            EntregoStorage.addRecentSearch(nextValue.getFullText(null).toString())

        }
    }
}
