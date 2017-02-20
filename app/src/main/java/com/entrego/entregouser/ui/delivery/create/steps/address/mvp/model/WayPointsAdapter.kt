package com.entrego.entregouser.ui.delivery.create.steps.address.mvp.model

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.delivery.create.steps.address.mvp.view.FieldClickListener
import java.util.*

class WayPointsAdapter(val mFieldClickListener: FieldClickListener) : RecyclerView.Adapter<WayPointsAdapter.ViewHolder>() {


    companion object

    private

    val dataset = LinkedList<String>()

    fun addPoint() {
        dataset.add("")
        notifyItemChanged(dataset.lastIndex)
    }

    fun removePoint() {
        if (dataset.size > 0) {
            val removedIndex = dataset.lastIndex
            dataset.removeAt(dataset.lastIndex)
            notifyItemRemoved(removedIndex)
        }
    }

    fun getAddressList(): List<String> = dataset

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var addressNext: TextView? = null
        var index: Int? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.include_select_address, parent, false)
        // set the view's size, margins, paddings and layout parameters
        val vh = ViewHolder(v)
        val view = v.findViewById(R.id.places_autocomplete_next) as TextView
        vh.addressNext = view
        dataset[dataset.lastIndex] = vh.addressNext?.text.toString()
        vh.addressNext?.setOnClickListener { mFieldClickListener.openAutocompleteActivity(it as TextView) }

        return vh
    }

    override fun getItemCount(): Int = dataset.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addressNext?.text = dataset[position]
        holder.addressNext?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dataset[position] = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })
    }
}
