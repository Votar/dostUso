package com.entrego.entregouser.entity.route

class EntregoRouteModel(val path: EntregoPath,
                        var waypoints: Array<EntregoPointBinding>) {



    fun getCurrentPoint(): EntregoPointBinding {
        var currentPoint = waypoints.findLast { (it.status == PointStatus.GOING && waypoints.indexOf(it) != waypoints.lastIndex) }
        if (currentPoint == null) {
            currentPoint = waypoints.findLast { (it.status == PointStatus.WAITING && waypoints.indexOf(it) != waypoints.lastIndex) }
            if (currentPoint == null) {
                currentPoint = waypoints.find { (it.status == PointStatus.PENDING && waypoints.indexOf(it) != waypoints.lastIndex) }
                if (currentPoint == null) {
                    currentPoint = waypoints.findLast { (it.status == PointStatus.DONE && waypoints.indexOf(it) != waypoints.lastIndex) }
                    if (currentPoint == null)
                        return waypoints[0]
                }
            }
        }
        return currentPoint
    }

    fun getGoingPoint(): EntregoPointBinding {
        val currentPoint = getCurrentPoint()
        if (currentPoint.status == PointStatus.DONE)
            return getDestinationPoint()
        else
            return currentPoint
    }

    fun getDestinationPoint(): EntregoPointBinding {
        val currentPointIndex = waypoints.indexOf(getCurrentPoint())
        return waypoints[currentPointIndex.plus(1)]
    }

    fun getNextPoint(): EntregoPointBinding? {
        val destinationIndex = waypoints.indexOf(getDestinationPoint())
        val waypointsLastIndex = waypoints.lastIndex
        if (destinationIndex != waypointsLastIndex)
            return waypoints[destinationIndex.plus(1)]
        else
            return null
    }
}

