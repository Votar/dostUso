package com.entrego.entregouser.entity.back

import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.entity.route.PointStatus

data class EntregoWaypoint(val waypoint: EntregoPointBinding,
                           val status: PointStatus)

fun Array<EntregoWaypoint>.getCurrentPoint(): EntregoWaypoint {
    var currentPoint = findLast { (it.status == PointStatus.GOING && indexOf(it) != lastIndex) }
    if (currentPoint == null) {
        currentPoint = findLast { (it.status == PointStatus.WAITING && indexOf(it) != lastIndex) }
        if (currentPoint == null) {
            currentPoint = find { (it.status == PointStatus.PENDING && indexOf(it) != lastIndex) }
            if (currentPoint == null) {
                currentPoint = findLast { (it.status == PointStatus.DONE && indexOf(it) != lastIndex) }
                if (currentPoint == null)
                    return this[0]
            }
        }
    }
    return currentPoint
}

fun Array<EntregoWaypoint>.getGoingPoint(): EntregoWaypoint {
    val currentPoint = getCurrentPoint()
    if (currentPoint.status == PointStatus.DONE)
        return getDestinationPoint()
    else
        return currentPoint
}

fun Array<EntregoWaypoint>.getDestinationPoint(): EntregoWaypoint {
    val currentPointIndex = indexOf(getCurrentPoint())
    return this[currentPointIndex.plus(1)]
}

fun Array<EntregoWaypoint>.getNextPoint(): EntregoWaypoint? {
    val destinationIndex = indexOf(getDestinationPoint())
    val historyLastIndex = lastIndex
    if (destinationIndex != historyLastIndex)
        return this[destinationIndex.plus(1)]
    else
        return null
}