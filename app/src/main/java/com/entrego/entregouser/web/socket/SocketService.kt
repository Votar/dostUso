package com.entrego.entregouser.web.socket

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import com.entrego.entregouser.R
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.storage.realm.models.CustomerProfileModel
import com.entrego.entregouser.ui.delivery.escort.chat.ChatMessengerActivity
import com.entrego.entregouser.util.GsonHolder
import com.entrego.entregouser.util.logd
import com.entrego.entregouser.web.socket.model.ChatSocketMessage


class SocketService : Service() {
    companion object {
        val KEY_EVENT = "key_event"
        val KEY_MESSAGE = "ext_key_message"
        val RECEIVED_KEY = "service_ext_key"
    }


    enum class SocketServiceEvents(val value: String) {
        CONNECT("connect"),
        DISCONNECT("disconnect"),
        SEND_TEXT("message")
    }

    var mSocketClient: SocketClient? = null
    var mUserProfile: CustomerProfileModel? = null
    var mReceiveMessagesListener = object : SocketContract.ReceiveMessagesListener {
        override fun receivedMessengerLocation(messageJson: String) {
            sendMessengerLocation(messageJson)
        }

        override fun receivedChatMessage(messageJson: String) {
            sendChatMessageEvent(messageJson)
            if (mUserProfile == null)
                mUserProfile = EntregoStorage.getProfile()
            GsonHolder
                    .instance
                    .fromJson(messageJson, ChatSocketMessage::class.java)
                    .apply {
                        if (mUserProfile?.id != sender)
                            sendChatMessageReceivedNotification(order, sender, text)
                    }
        }

        override fun receivedOrderUpdated(deliveryId: Long) {
            sendOrderUpdatedEvent(deliveryId)
        }

        override fun receivedDeliveryUpdated(deliveryId: Long) {
            sendDeliveryUpdatedEvent(deliveryId)
        }

    }

    private fun sendMessengerLocation(messageJson: String) {
        Intent(SocketContract.UpdateMessengerLocationEvent.ACTION).apply {
            putExtra(SocketContract.UpdateMessengerLocationEvent.KEY_MESSENGER_LOCATION, messageJson)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(this)
        }
    }

    private fun sendChatMessageEvent(messageJson: String) {
        Intent(SocketContract.ReceivedChatMessageBySocketEvent.ACTION).apply {
            putExtra(SocketContract.ReceivedChatMessageBySocketEvent.KEY_MESSAGE, messageJson)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(this)
        }

    }

    private fun sendOrderUpdatedEvent(deliveryId: Long) {
        val intent = Intent(SocketContract.UpdateOrderEvent.ACTION)
        intent.putExtra(SocketContract.UpdateOrderEvent.KEY_DELIVERY_ID, deliveryId)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }


    private fun sendDeliveryUpdatedEvent(deliveryId: Long) {
        logd("sent intent with deliveryId = $deliveryId")
        val intent = Intent(SocketContract.UpdateDeliveryEvent.ACTION)
        intent.putExtra(SocketContract.UpdateDeliveryEvent.KEY_DELIVERY_ID, deliveryId)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        val token = EntregoStorage.getTokenOrEmpty()
        mSocketClient = SocketClient(token, mReceiveMessagesListener)
        mSocketClient?.openConnection()
        mUserProfile = EntregoStorage.getProfile()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (applicationContext == null) stopSelf()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocketClient?.closeConnection()
        logd("SocketService destroyed")
    }

    fun sendChatMessageReceivedNotification(orderId: Long, userId: Long, message: String) {

        val mBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(this)
                        .setContentTitle(getString(R.string.notification_received_chat_message))
                        .setSmallIcon(R.drawable.map_user_pin)
                        .setContentText(message)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        mBuilder.setSound(alarmSound)

        val resultIntent = ChatMessengerActivity.getIntent(this, orderId, userId)

        val resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                )

        mBuilder.setContentIntent(resultPendingIntent)

        val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.notify(orderId.toInt(), mBuilder.build())

    }

}