package com.entrego.entregouser.web.socket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import com.entrego.entregouser.storage.EntregoStorage
import com.entrego.entregouser.util.logd

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
    var mReceiveMessagesListener = object : SocketContract.ReceiveMessagesListener {
        override fun receivedDeliveryUpdated(deliveryId: Long) {
            sendDeliveryUpdatedEvent(deliveryId)
        }
    }
//    val mReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (intent?.hasExtra(KEY_EVENT) == true)
//                when (intent.getStringExtra(KEY_EVENT)) {
//                    SocketServiceEvents.CONNECT.value -> {
//                        mSocketClient?.openConnection()
//                    }
//                    SocketServiceEvents.DISCONNECT.value -> mSocketClient?.closeConnection()
//                    SocketServiceEvents.SEND_TEXT.value -> {
//
//                    }
//                }
//        }
//
//    }

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
        if (applicationContext == null)
            stopSelf()
        val token = EntregoStorage.getTokenOrEmpty()
        mSocketClient = SocketClient(token, mReceiveMessagesListener)
        mSocketClient?.openConnection()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocketClient?.closeConnection()
        logd("SocketService destroyed")
    }


}