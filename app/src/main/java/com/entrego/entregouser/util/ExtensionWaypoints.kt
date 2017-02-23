package com.entrego.entregouser.util

import com.entrego.entregouser.entity.route.EntregoPointBinding
import com.entrego.entregouser.entity.route.PointStatus


fun Array<EntregoPointBinding>.getCurrentPoint(): EntregoPointBinding {
    var currentPoint = this.findLast { (it.status == PointStatus.GOING && this.indexOf(it) != this.lastIndex) }
    if (currentPoint == null) {
        currentPoint = this.findLast { (it.status == PointStatus.WAITING && this.indexOf(it) != this.lastIndex) }
        if (currentPoint == null) {
            currentPoint = this.find { (it.status == PointStatus.PENDING && this.indexOf(it) != this.lastIndex) }
            if (currentPoint == null) {
                currentPoint = this.findLast { (it.status == PointStatus.DONE && this.indexOf(it) != this.lastIndex) }
                if (currentPoint == null)
                    return this[0]
            }
        }
    }
    return currentPoint
}