package com.entrego.entregouser.entity.common

import com.entrego.entregouser.entity.route.EntregoPointBinding

class EntregoStatusModel (val messenger: EntregoMessengerView,
                          val waypoints: Array<EntregoPointBinding>){
}