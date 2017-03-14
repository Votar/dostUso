package com.entrego.entregouser.web.socket

import android.os.Handler
import com.entrego.entregouser.util.GsonHolder
import com.entrego.entregouser.util.logd
import com.entrego.entregouser.web.socket.model.BaseSocketMessage
import com.entrego.entregouser.web.socket.model.SocketMessageType
import com.entrego.entregouser.web.socket.model.UpdateDeliverySocketMessage
import com.google.gson.reflect.TypeToken
import com.neovisionaries.ws.client.*
import entrego.com.android.web.api.EntregoApi
import java.lang.IllegalStateException
import java.lang.reflect.Type

class SocketClient(token: String, val serverListener: SocketContract.ReceiveMessagesListener) {

    companion object {
        @JvmStatic
        fun getHashMapType(): Type =
                object : TypeToken<HashMap<String, String>>() {
                }.type

    }


    val END_POINT = "ws://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/status"
    val TIMEOUT = 5000 //5sec
    var mSocketConnection: WebSocket? = null
    val mGson = GsonHolder.instance

    init {
        mSocketConnection = WebSocketFactory()
                .createSocket(END_POINT, TIMEOUT)
                .addHeader(EntregoApi.TOKEN, token)
                .addListener(SocketListener())
    }

    inner class SocketListener : WebSocketAdapter() {
        val TAG = "SOCKET_RECEIVER"
        val TAG_ERROR = "SOCKET_ERROR"
        val RECONNECT_TIMEOUT = 5000 // sec

        override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
            logd(TAG, "Socket disconnected")
        }

        override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
            super.onConnected(websocket, headers)
            logd(TAG, "Socket connected")
        }

        override fun onTextMessage(websocket: WebSocket?, text: String) {
            super.onTextMessage(websocket, text)
//            logd(TAG, text)
            parseMessage(text)
        }

        override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
            super.onConnectError(websocket, exception)
            Handler().postDelayed({ connectAsync() }, 1500)
            logd(TAG_ERROR, exception?.error.toString())
        }

        fun parseMessage(json: String) {

            val baseMessage = GsonHolder.instance.fromJson(json, BaseSocketMessage::class.java)

            when (baseMessage.type) {
                SocketMessageType.WAYPOINT,
                SocketMessageType.ORDER_STATUS -> {
                    logd(TAG, json)
                    val updateDeliveryModel = GsonHolder
                            .instance
                            .fromJson(json, UpdateDeliverySocketMessage::class.java)
                    logd(TAG, updateDeliveryModel.toString())
                    serverListener.receivedDeliveryUpdated(updateDeliveryModel.delivery)
                }
                SocketMessageType.ORDER -> logd(TAG, json)
                SocketMessageType.TRACK -> logd(TAG, json)
                SocketMessageType.TRACK_LIST -> logd(TAG, json)
                else -> IllegalStateException("Invalid type of socket message")
            }
        }
    }


    fun openConnection() {
        connectAsync()
    }

    private fun connectAsync() {
        mSocketConnection?.disconnect()
        //new socketConnection
        mSocketConnection = mSocketConnection
                ?.recreate(0)
                ?.connectAsynchronously()
    }

    fun closeConnection() {
        mSocketConnection = mSocketConnection?.disconnect()
    }


}

