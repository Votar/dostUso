package com.entrego.entregouser.web.socket.model

import com.google.android.gms.maps.model.LatLng

data class TrackModel(val id: Long,
                      val type: SocketMessageType,
                      val coordinates: LatLng)