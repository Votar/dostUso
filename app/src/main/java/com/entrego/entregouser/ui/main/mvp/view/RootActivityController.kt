package com.entrego.entregouser.ui.main.mvp.view

import android.app.Fragment

/**
 * Created by Admin on 01.02.2017.
 */
interface RootActivityController {
    fun showSelectAddress()
    fun showFragment(fragment: Fragment)
}