package com.entrego.entregouser.entity.route

class EntregoPoint(val latitude: Double = 0.0,
                   val longitude: Double = 0.0,
                   var address: String? = "",
                   var status: String? = "") {
}