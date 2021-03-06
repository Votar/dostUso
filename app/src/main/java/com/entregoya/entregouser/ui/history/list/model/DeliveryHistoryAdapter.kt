package com.entregoya.entregouser.ui.history.list.model

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.entregoya.entregouser.R
import com.entregoya.entregouser.databinding.ItemDeliveryPreviewBinding
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.route.EntregoRouteModel
import com.entregoya.entregouser.util.getStaticMapUrlWithWaypoints
import com.entregoya.entregouser.util.loadMessengerPicWithToken
import com.entregoya.entregouser.util.logd

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
            logd(currentModel.toString())
            val url = buildUrlForStaticMap(currentModel.route)
            logd(url)
            Glide.with(ctx)
                    .load(url)
                    .skipMemoryCache(true)
                    .error(R.drawable.ic_cloud_off_48dp)
                    .into(holder.binder?.historyRoutesStaticMap)
            if (currentModel.order?.messenger != null)
            {
                holder.binder?.messengerPic?.loadMessengerPicWithToken(currentModel.order.messenger.id)
            }

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
        return route.getStaticMapUrlWithWaypoints()
    }
}