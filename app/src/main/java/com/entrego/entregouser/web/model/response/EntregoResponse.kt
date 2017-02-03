package entrego.com.android.web.model.response

import android.service.voice.AlwaysOnHotwordDetector

/**
 * Created by bertalt on 29.11.16.
 */
open class EntregoResponse(open val code: Int?,
                           open val message: String?) {

    override fun toString(): String {
        return "EntregoResponse(code=$code, message=$message)"
    }
}