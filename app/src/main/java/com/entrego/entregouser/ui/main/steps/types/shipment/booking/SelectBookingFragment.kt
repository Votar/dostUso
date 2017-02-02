package com.entrego.entregouser.ui.main.steps.types.shipment.booking

import android.app.Fragment
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entrego.entregouser.R
import com.entrego.entregouser.util.showSnack
import java.util.*

class SelectBookingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_shipment_booking, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showTimePickerDialog()
    }

    fun showTimePickerDialog() {
        val nowDateTime = Calendar.getInstance()

        val pickerDialog = TimePickerDialog(activity,
                R.style.DialogTheme,
                mTimePickerListener,
                nowDateTime.get(Calendar.HOUR_OF_DAY),
                nowDateTime.get(Calendar.MINUTE),
                true)

        pickerDialog.show()
    }

    var mTimePickerListener: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

        val nowTime = Calendar.getInstance()
        val selectedTime = Calendar.getInstance()
        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        selectedTime.set(Calendar.MINUTE, minute)

        if (nowTime.timeInMillis > selectedTime.timeInMillis) {
            view.showSnack(getString(R.string.error_time_in_past))
        }
    }
}

