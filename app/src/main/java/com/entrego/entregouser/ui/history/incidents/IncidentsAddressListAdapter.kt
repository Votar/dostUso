package com.entrego.entregouser.ui.history.incidents

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.back.EntregoWaypoint
import com.entrego.entregouser.ui.delivery.escort.status.loadDrawable

class IncidentsAddressListAdapter(val dataset: List<EntregoWaypoint>) : RecyclerView.Adapter<IncidentsAddressListAdapter.ViewHolder>() {

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        internal var tvAddress: TextView? = null
        internal var ivPin: ImageView? = null
        internal var vBottomLine: View? = null
        internal var vTopLine: View? = null


        init {
            tvAddress = rootView.findViewById(R.id.status_address_text) as TextView
            ivPin = rootView.findViewById(R.id.status_address_pin) as ImageView
            vBottomLine = rootView.findViewById(R.id.status_address_bottom_line)
            vTopLine = rootView.findViewById(R.id.status_address_top_line)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val currentModel = dataset[position]

        holder?.tvAddress?.text = currentModel.waypoint.address
        var idDrawable: Int
        if (position == dataset.lastIndex) {
            holder?.vBottomLine?.visibility = View.GONE
            holder?.ivPin?.loadDrawable(R.drawable.red_dot)
        } else if (position == 0) {
            holder?.vTopLine?.visibility = View.GONE
            holder?.ivPin?.loadDrawable(R.drawable.blue_dot)

        } else {
            holder?.ivPin?.loadDrawable(R.drawable.grey_dot)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_address_list, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = dataset.count()

}