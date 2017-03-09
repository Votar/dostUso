package com.entrego.entregouser.entity.back

import com.entrego.entregouser.entity.common.EntregoMessengerView
import java.util.*

class EntregoOrderView(
        val waypoints: Array<EntregoWaypoint>,
        val messenger: EntregoMessengerView) {

    override fun toString(): String {
        return "EntregoOrderView(waypoints=${Arrays.toString(waypoints)}, messenger=$messenger)"
    }
}