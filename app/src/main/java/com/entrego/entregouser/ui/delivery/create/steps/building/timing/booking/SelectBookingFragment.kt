package com.entrego.entregouser.ui.delivery.create.steps.building.timing.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entrego.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entrego.entregouser.ui.delivery.create.steps.address.SelectAddressFragment
import com.facebook.internal.Utility.logd
import java.util.*

class SelectBookingFragment : BaseBuilderFragment() {

    val TAG = "SelectBookingFragment"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_shipment_booking, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showDatePickerDialog()
    }

    fun showDatePickerDialog() {
        val nowDateTime = Calendar.getInstance()

        val mDatePickerListener: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->

            val selectedDateTime = Calendar.getInstance()
            selectedDateTime.set(Calendar.YEAR, year)
            selectedDateTime.set(Calendar.MONTH, month)
            selectedDateTime.set(Calendar.DAY_OF_MONTH, day)
            if (nowDateTime.timeInMillis > selectedDateTime.timeInMillis) {
                showErrorSnackToDate()
            } else {
                showTimePickerDialog(selectedDateTime)
            }

        }

        activity?.let {
            val datePickerDialog = DatePickerDialog(activity,
                    R.style.DialogTheme,
                    mDatePickerListener,
                    nowDateTime.get(Calendar.YEAR),
                    nowDateTime.get(Calendar.MONTH),
                    nowDateTime.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }
    }

    fun showTimePickerDialog(selectedDate: Calendar) {
        val nowDateTime = Calendar.getInstance()

        val mTimePickerListener: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            val nowTime = Calendar.getInstance()
            val resultTime = selectedDate.clone() as Calendar
            resultTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            resultTime.set(Calendar.MINUTE, minute)

            if (nowTime.timeInMillis > resultTime.timeInMillis) {
                showErrorSnackToTime(selectedDate)
            } else {
                logd(TAG, resultTime.toString())
                prepareNextFragment(SelectAddressFragment(), FragmentType.ADDRESS)
            }
        }
        activity?.let {
            val pickerDialog = TimePickerDialog(activity,
                    R.style.DialogTheme,
                    mTimePickerListener,
                    nowDateTime.get(Calendar.HOUR_OF_DAY),
                    nowDateTime.get(Calendar.MINUTE),
                    true)

            pickerDialog.show()
        }
    }

    fun showErrorSnackToDate() {
        val snackBar = Snackbar.make(view, getString(R.string.error_date_in_past), Snackbar.LENGTH_INDEFINITE)
        val sbMessageTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        val primaryColor = ContextCompat.getColor(activity, R.color.colorTomato)
        sbMessageTextView.setTextColor(primaryColor)
        snackBar.setAction(R.string.text_ok,
                { showDatePickerDialog() })
        snackBar.show()
    }

    fun showErrorSnackToTime(selectedDate: Calendar) {
        val snackBar = Snackbar.make(view, getString(R.string.error_time_in_past), Snackbar.LENGTH_INDEFINITE)
        val sbMessageTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        val primaryColor = ContextCompat.getColor(activity, R.color.colorTomato)
        sbMessageTextView.setTextColor(primaryColor)
        snackBar.setAction(R.string.text_ok,
                { showTimePickerDialog(selectedDate) })
        snackBar.show()
    }

}

