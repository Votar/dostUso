package com.entrego.entregouser.web.socket

import android.os.Handler
import com.entrego.entregouser.util.GsonHolder
import com.entrego.entregouser.util.logd
import com.entrego.entregouser.web.socket.model.BaseSocketMessage
import com.entrego.entregouser.web.socket.model.SocketMessageType
import com.neovisionaries.ws.client.*
import java.lang.IllegalStateException

class SocketClient(val serverListener: SocketContract.ReceiveMessagesListener) {


    val END_POINT = "ws://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/status"
    val TIMEOUT = 5000 //5sec
    var mSocketConnection: WebSocket? = null
    val mGson = GsonHolder.instance
    var isNeed: Boolean

    init {
        mSocketConnection = WebSocketFactory()
                .createSocket(END_POINT, TIMEOUT)
                .addListener(SocketListener())
        isNeed = false
    }

    inner class SocketListener : WebSocketAdapter() {
        val TAG = "SOCKET_RECEIVER"
        val TAG_ERROR = "SOCKET_ERROR"
        val RECONNECT_TIMEOUT = 5000 // sec

        override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
            logd(TAG, "Socked disconnected \n is need - $isNeed")
        }

        override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
            super.onConnected(websocket, headers)
            logd(TAG, "Socket connected")
        }

        override fun onTextMessage(websocket: WebSocket?, text: String) {
            super.onTextMessage(websocket, text)
            parseMessage(text)
        }

        override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
            super.onConnectError(websocket, exception)
            when (exception?.error) {
                WebSocketError.NOT_IN_CREATED_STATE ->
                    if (isNeed) {
                        logd(TAG_ERROR, "Should reconnect after failure connect attemp")
                        Handler().postDelayed({ connectAsync() }, 1500)
                    }
                else -> {
                    logd(TAG_ERROR, exception?.error.toString())
                }
            }
        }

        fun parseMessage(json: String) {
            val base = GsonHolder.instance.fromJson(json, BaseSocketMessage::class.java)
            when (base.type) {
                SocketMessageType.WAYPOINT, SocketMessageType.ORDER_STATUS -> {
                    //TODO: Will with delivery Id for filtering updates
                    serverListener.receivedDeliveryUpdated(0)
                }
                SocketMessageType.ORDER -> logd(TAG, json)
                SocketMessageType.TRACK  -> logd(TAG, json)
                SocketMessageType.TRACK_LIST -> logd(TAG, json)
                else -> IllegalStateException("Invalid type of socket message")
            }

        }
    }


    fun openConnection() {
        isNeed = true
        connectAsync()
    }

    private fun connectAsync() {
        mSocketConnection?.disconnect()
        mSocketConnection = mSocketConnection?.recreate(0)
        mSocketConnection = mSocketConnection?.connectAsynchronously()
    }

    fun closeConnection() {
        isNeed = false
        mSocketConnection = mSocketConnection?.disconnect()
    }




}