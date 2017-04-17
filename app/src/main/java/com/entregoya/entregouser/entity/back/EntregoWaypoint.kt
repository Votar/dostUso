package com.entregoya.entregouser.entity.back

import com.entregoya.entregouser.entity.route.EntregoPointBinding
import com.entregoya.entregouser.entity.route.PointStatus

class EntregoWaypoint(val waypoint: EntregoPointBinding,
                           val status: PointStatus)

fun Array<EntregoPointBinding>.getCurrentPoint(): EntregoPointBinding {
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


fun Array<EntregoPointBinding>.getDestinationPoint(): EntregoPointBinding {
    val currentPointIndex = indexOf(getCurrentPoint())
    return this[currentPointIndex.plus(1)]
}


fun Array<EntregoPointBinding>.getOtherPoints(): List<EntregoPointBinding> {

    val indexNextPoint = indexOf(getDestinationPoint()).plus(1)
    if (indexNextPoint <= lastIndex)
        return asList().subList(indexNextPoint, count())
    else
        return emptyList<EntregoPointBinding>()
}

fun Array<EntregoPointBinding>.getNextPoint(): EntregoPointBinding? {
    val destinationIndex = indexOf(getDestinationPoint())
    val historyLastIndex = lastIndex
    if (destinationIndex != historyLastIndex)
        return this[destinationIndex.plus(1)]
    else
        return null
}


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


fun Array<EntregoWaypoint>.getDestinationPoint(): EntregoWaypoint {
    val currentPointIndex = indexOf(getCurrentPoint())
    return this[currentPointIndex.plus(1)]
}


fun Array<EntregoWaypoint>.getOtherPoints(): List<EntregoWaypoint> {

    val indexNextPoint = indexOf(getDestinationPoint()).plus(1)
    if (indexNextPoint <= lastIndex)
        return asList().subList(indexNextPoint, count())
    else
        return emptyList<EntregoWaypoint>()
}

fun Array<EntregoWaypoint>.getNextPoint(): EntregoWaypoint? {
    val destinationIndex = indexOf(getDestinationPoint())
    val historyLastIndex = lastIndex
    if (destinationIndex != historyLastIndex)
        return this[destinationIndex.plus(1)]
    else
        return null
}
