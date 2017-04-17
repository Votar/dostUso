package com.entregoya.entregouser.web.socket

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import com.entregoya.entregouser.R
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.storage.realm.models.CustomerProfileModel
import com.entregoya.entregouser.ui.delivery.escort.chat.ChatMessengerActivity
import com.entregoya.entregouser.util.GsonHolder
import com.entregoya.entregouser.util.logd
import com.entregoya.entregouser.web.socket.model.ChatSocketMessage
import java.util.*

class SocketService : Service() {

    companion object

    var mSocketClient: SocketClient? = null
    var mUserProfile: CustomerProfileModel? = null
    val TIMER_INTERVAL = 10000L //10 sec
    var mKeepAliveSocketTimer: Timer? = null


    val mReceiveMessagesListener = object : SocketContract.ReceiveMessagesListener {
        override fun disconnectedByServer() {
            stopTimer()
            mSocketClient?.closeConnection()
            stopSelf()
        }

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
                            sendChatMessageReceivedNotification(order, subscriber, text)
                    }
        }

        override fun receivedOrderUpdated(deliveryId: Long) {
            sendOrderUpdatedEvent(deliveryId)
        }

        override fun receivedDeliveryUpdated(deliveryId: Long) {
            sendDeliveryUpdatedEvent(deliveryId)
        }

    }

    private fun sendChatMessageEvent(messageJson: String) {
        Intent(SocketContract.ReceivedChatMessageBySocketEvent.ACTION).apply {
            putExtra(SocketContract.ReceivedChatMessageBySocketEvent.KEY_MESSAGE, messageJson)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(this)
        }

    }

    private fun sendMessengerLocation(messageJson: String) {
        Intent(SocketContract.UpdateMessengerLocationEvent.ACTION).apply {
            putExtra(SocketContract.UpdateMessengerLocationEvent.KEY_MESSENGER_LOCATION, messageJson)
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
        throw TODO()
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        checkTimer()
        return START_STICKY
    }

    private fun checkTimer() {
        if (mKeepAliveSocketTimer == null)
            startTimer()
    }

    fun stopTimer() {
        mKeepAliveSocketTimer?.cancel()
        mKeepAliveSocketTimer?.purge()
        mKeepAliveSocketTimer = null
    }

    fun startTimer() {
        stopTimer()
        mKeepAliveSocketTimer = Timer()
        mKeepAliveSocketTimer?.schedule(object : TimerTask() {
            override fun run() {
                if (mSocketClient?.inOpen() != true)
                    startSocket()
            }
        }, 0, TIMER_INTERVAL
        )
    }

    fun startSocket() {
        mSocketClient?.closeConnection()
        val token = EntregoStorage.getTokenOrEmpty()
        if (token.isNullOrEmpty())
            stopSelf()
        mSocketClient = SocketClient(token, mReceiveMessagesListener)
        mSocketClient?.openConnection()
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

    override fun onDestroy() {
        super.onDestroy()
        mSocketClient?.closeConnection()
        stopTimer()
        logd("SocketService destroyed")
    }

}