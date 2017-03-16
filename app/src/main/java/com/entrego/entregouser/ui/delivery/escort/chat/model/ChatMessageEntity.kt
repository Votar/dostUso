package com.entrego.entregouser.ui.delivery.escort.chat.model

import java.util.*

data class ChatMessageEntity(val text: String,
                             val userType: UserType,
                             val timestamp: Long = Calendar.getInstance().time.time)