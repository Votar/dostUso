package com.entregoya.entregouser.web.api


object EntregoApi {


    const val BASE_URL = "http://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/"
    const val CONTENT_JSON = "content-type: application/json"
    const val TOKEN = "x-auth-token"

    const val URL_MESSENGER_PIC = BASE_URL+ "customer/image/messenger/"
    const val URL_CUSTOMER_PIC = BASE_URL + "customer/image/customer/"

}