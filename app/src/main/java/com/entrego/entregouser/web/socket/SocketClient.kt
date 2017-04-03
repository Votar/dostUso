package com.entrego.entregouser.web.socket

import android.os.Handler
import com.entrego.entregouser.util.GsonHolder
import com.entrego.entregouser.util.logd
import com.entrego.entregouser.web.api.EntregoApi
import com.entrego.entregouser.web.socket.model.BaseSocketMessage
import com.entrego.entregouser.web.socket.model.SocketMessageType
import com.entrego.entregouser.web.socket.model.UpdateDeliverySocketMessage
import com.neovisionaries.ws.client.*

class SocketClient(token: String, val serverListener: SocketContract.ReceiveMessagesListener) {

    val END_POINT = "ws://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/status"
    val TIMEOUT = 5000 //5sec
    var mSocketConnection: WebSocket? = null
    val mGson = GsonHolder.instance
    private var isNeed = false

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
            logd(TAG, "Socket disconnected is need keep alive $isNeed")
            if (isNeed)
                Handler().postDelayed({ connectAsync() }, 1500)


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
        }

        fun parseMessage(json: String) {

            val baseMessage = GsonHolder.instance.fromJson(json, BaseSocketMessage::class.java)
            when (baseMessage.type) {
                SocketMessageType.ORDER_STATUS -> {
                    val updateOrderModel = GsonHolder
                            .instance
                            .fromJson(json, UpdateDeliverySocketMessage::class.java)
                    logd(TAG, updateOrderModel.toString())
                    serverListener.receivedOrderUpdated(updateOrderModel.delivery)
                }
                SocketMessageType.WAYPOINT -> {
                    val updateDeliveryModel = GsonHolder
                            .instance
                            .fromJson(json, UpdateDeliverySocketMessage::class.java)
                    logd(TAG, updateDeliveryModel.toString())
                    serverListener.receivedDeliveryUpdated(updateDeliveryModel.delivery)
                }
                SocketMessageType.ORDER -> {
                    logd(TAG, json)
                }
                SocketMessageType.TRACK -> {
                    serverListener.receivedMessengerLocation(json)
                    logd(TAG, json)
                }
                SocketMessageType.TRACK_LIST -> logd(TAG, json)
                SocketMessageType.MESSAGE -> {
                    logd(json)
                    serverListener.receivedChatMessage(json)
                }
                else -> IllegalStateException("Invalid type of socket message")
            }
        }
    }

    fun inOpen(): Boolean = (mSocketConnection?.isOpen == true)


    fun openConnection() {
        isNeed = true
        connectAsync()
    }

    private fun connectAsync() {
        if (mSocketConnection?.isOpen == true) return
        //new socketConnection
        mSocketConnection = mSocketConnection
                ?.recreate(TIMEOUT)

        mSocketConnection?.connectAsynchronously()
    }


    fun closeConnection() {
        isNeed = false
        mSocketConnection?.disconnect()
    }


}

