package com.entrego.entregouser.ui.delivery.escort.chat.model

data class ChatMessageEntity(val timestamp:Long,
                             val text:String,
                             val userType:UserType) {
}