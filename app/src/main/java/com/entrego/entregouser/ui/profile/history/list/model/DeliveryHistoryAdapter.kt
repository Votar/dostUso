package com.entrego.entregouser.ui.profile.history.list.model

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.entrego.entregouser.R
import com.entrego.entregouser.databinding.ItemDeliveryPreviewBinding
import com.entrego.entregouser.entity.route.EntregoRouteModel
import com.entrego.entregouser.util.getStaticMapUrl
import com.entrego.entregouser.util.logd

class DeliveryHistoryAdapter(val dataset: List<EntregoDeliveryPreview>, val listener: ClickItemListener) : RecyclerView.Adapter<DeliveryHistoryAdapter.ViewHolder>() {

    interface ClickItemListener {
        fun onItemClicked(delivery: EntregoDeliveryPreview)
    }

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var binder: ItemDeliveryPreviewBinding? = null

        init {
            binder = DataBindingUtil.bind(rootView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val currentModel = dataset[position]
        holder?.binder?.model = currentModel

        val ctx = holder?.itemView?.context
        if (ctx != null) {
            val url = currentModel.ulrPic
            logd(url)
            Glide.with(ctx)
                    .load(url)
                    .error(R.drawable.ic_cloud_off_48dp)
                    .into(holder?.binder?.historyRoutesStaticMap)

        }
        holder?.binder?.root?.setOnClickListener { listener.onItemClicked(currentModel) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemDeliveryPreviewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }

    fun buildUrlForStaticMap(route: EntregoRouteModel): String {
        val path = route.path.line
        return route.waypoints.getStaticMapUrl(path)
    }
}