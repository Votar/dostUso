package com.entregoya.entregouser.web.api

interface ApiContract {
    companion object {
        const val RESPONSE_OK = 0
        const val FIELD_ERROR = 1
        const val RESPONSE_INVALID_TOKEN = 2
        const val INCORRECT_ORDER_STATE = 9
    }
}