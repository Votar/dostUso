package com.entrego.entregouser.entity.order

enum class OrderStatus {
    PENDING,
    ACCEPTED,
    FINISHED,
    CANCELLED,
    DECLINED;


    override fun toString(): String {
        return super.toString()
    }
}