package com.entrego.entregouser.entity.delivery

enum class EntregoDeliveryStatuses {
    PENDING,
    ASSIGNED,
    DELIVERED,
    CANCELED,
    CONFIRMATION;

    override fun toString(): String {
        return super.toString()
    }
}