package com.entrego.entregouser.ui.main.steps.address.mvp.model

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.main.steps.address.mvp.view.FieldClickListener
import java.util.*

class WayPointsAdapter(val mFieldClickListener: FieldClickListener) : RecyclerView.Adapter<WayPointsAdapter.ViewHolder>() {


    companion object

    val dataset = ArrayList<Pair<String, String>>()

    init {
        dataset.add(Pair("Otak", "Nado"))
    }

    fun addPoint() {
        dataset.add(Pair("", ""))
        notifyDataSetChanged()
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var addressFrom: TextView? = null
        var addressTo: TextView? = null
        var swipeButton: ImageView? = null

        constructor(rootView: View) : super(rootView)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.include_select_address, parent, false)
        // set the view's size, margins, paddings and layout parameters
        val vh = ViewHolder(v)
        vh.addressFrom = v.findViewById(R.id.places_autocomplete_from) as TextView
        vh.addressTo = v.findViewById(R.id.places_autocomplete_to) as TextView
        vh.swipeButton = v.findViewById(R.id.places_swipe_btn) as ImageView

        vh.addressFrom?.setOnClickListener { mFieldClickListener.openAutocompleteActivity(it as TextView) }
        vh.addressTo?.setOnClickListener { mFieldClickListener.openAutocompleteActivity(it as TextView) }
        vh.swipeButton?.setOnClickListener {
            val tmpText = vh.addressFrom!!.text
            vh.addressFrom?.text = vh.addressTo?.text
            vh.addressTo?.text = tmpText
        }
        return vh
    }

    override fun getItemCount(): Int = dataset.count()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }
}
