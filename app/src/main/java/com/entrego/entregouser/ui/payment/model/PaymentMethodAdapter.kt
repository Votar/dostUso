package com.entrego.entregouser.ui.payment.model

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.common.PaymentMethodEntity
import com.entrego.entregouser.entity.common.PaymentMethodType
import com.entrego.entregouser.web.model.response.card.EntregoCreditCardEntity
import java.util.*


class PaymentMethodAdapter(val dataset: LinkedList<Pair<PaymentMethodEntity, Boolean>>,
                           val clickListener: OnPaymentClickListener) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {
    var recentCheck: CheckedTextView? = null
    var lastCheckedPosition: Int = -1

    init {
        val defaultMethod = dataset.find { it.second }
        if (defaultMethod != null)
            lastCheckedPosition = dataset.indexOf(defaultMethod)
        else
            throw IllegalStateException("No default method in adapter")
    }

    interface OnPaymentClickListener {
        fun onClick(method: PaymentMethodEntity)
    }

    class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
        var title: CheckedTextView? = null
        var icon: ImageView? = null
    }

    fun addCards(cardList: List<EntregoCreditCardEntity>) {
        cardList.forEachIndexed { index, cardEntity ->
            val exist = dataset.find {
                it.first.card?.token?.equals(cardEntity.token) == true
            }
            if (exist == null) {
                val newPaymentMethod = PaymentMethodEntity(PaymentMethodType.CARD, cardEntity)
                dataset.add(Pair(newPaymentMethod, false))
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_credit_card, parent, false)

        // set the view's size, margins, paddings and layout parameters
        val vh = ViewHolder(v)

        vh.title = v.findViewById(R.id.item_payment_title) as CheckedTextView
        vh.icon = v.findViewById(R.id.item_card_icon) as ImageView
        return vh
    }

    fun getCheckedPaymentMethod(): PaymentMethodEntity = dataset[lastCheckedPosition].first

    override fun getItemCount(): Int = dataset.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nextValue = dataset[position]
        if (lastCheckedPosition == position)
            holder.title?.setCheckMarkDrawable(R.drawable.ic_check_blue_24dp)
        else
            holder.title?.checkMarkDrawable = null

        holder.title?.setOnClickListener { v ->
            (v as CheckedTextView).apply {
                clickListener.onClick(nextValue.first)
                lastCheckedPosition = position
                notifyDataSetChanged()
            }
        }
        when (nextValue.first.type) {
            PaymentMethodType.CASH -> holder.title.setupText(R.string.text_cash)
            PaymentMethodType.WALLET -> holder.title.setupText(R.string.text_wallet)
            PaymentMethodType.CLAVE -> holder.title.setupText(R.string.text_clave)
            PaymentMethodType.CARD -> buildCardView(holder, nextValue.first.card!!)
        }
    }


    fun buildCardView(holder: ViewHolder, card: EntregoCreditCardEntity) {

        holder.icon?.visibility = View.VISIBLE
        holder.title?.text = card.mask

        val firstDigit = card.mask.first()

        when (firstDigit) {
            '4' -> {
                val icon = ContextCompat.getDrawable(holder.title?.context, R.drawable.ic_visa)
                holder.icon?.setImageDrawable(icon)
            }
            '5' -> {
                val icon = ContextCompat.getDrawable(holder.title?.context, R.drawable.cio_ic_mastercard)
                holder.icon?.setImageDrawable(icon)
            }
        }

    }
}

fun TextView?.setupText(stringId: Int) {
    this?.text = this?.context?.getString(stringId)
}
