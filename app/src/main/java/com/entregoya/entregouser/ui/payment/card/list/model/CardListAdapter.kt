package com.entregoya.entregouser.ui.payment.card.list.model

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.entregoya.entregouser.R
import com.entregoya.entregouser.web.model.response.card.EntregoCreditCardEntity
import java.util.*


class CardListAdapter(val mFieldClickListener: OnItemClicked) : RecyclerView.Adapter<CardListAdapter.ViewHolder>() {

    private val dataset = LinkedList<EntregoCreditCardEntity>()

    interface OnItemClicked {
        fun onClick(card: EntregoCreditCardEntity)
    }

    class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
        var cardMask: TextView? = null
        var icon: ImageView? = null
    }

    fun swapDataset(newList: List<EntregoCreditCardEntity>) {
        dataset.clear()
        dataset.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_credit_card, parent, false)

        // set the view's size, margins, paddings and layout parameters
        val vh = ViewHolder(v)

        vh.cardMask = v.findViewById(R.id.item_payment_title) as TextView
        vh.icon = v.findViewById(R.id.item_card_icon) as ImageView
        return vh
    }

    override fun getItemCount(): Int = dataset.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nextValue = dataset[position]

        holder.icon?.visibility = View.VISIBLE

        holder.cardMask?.text = nextValue.mask
        val firstDigit = nextValue.mask.first()
        when (firstDigit) {
            '4' -> {
                val icon = ContextCompat.getDrawable(holder.icon?.context, R.drawable.ic_visa)
                holder.icon?.setImageDrawable(icon)
            }
            '5' -> {
                val icon = ContextCompat.getDrawable(holder.icon?.context, R.drawable.ic_mastercard)
                holder.icon?.setImageDrawable(icon)
            }
        }


        holder.rootView.setOnClickListener { mFieldClickListener.onClick(nextValue) }
    }

}
