package com.entregoya.entregouser.ui.delivery.create.mvp.presenter

import android.app.Activity
import android.content.Intent
import com.entregoya.entregouser.R
import com.entregoya.entregouser.ui.comon.AboutEntregoActivity
import com.entregoya.entregouser.ui.comon.ContactUsActivity
import com.entregoya.entregouser.ui.comon.DescriptionWithTopicActivity
import com.entregoya.entregouser.ui.comon.WorkForUsActivity
import com.entregoya.entregouser.ui.delivery.create.mvp.view.IRootView
import com.entregoya.entregouser.ui.faq.FaqListActivity
import com.entregoya.entregouser.ui.manual.ManualActivity
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import java.util.*

class RootPresenter : IRootPresenter {
    override fun isViewAvailable(): Boolean = mView != null

    override fun showUserManual() {
        mView?.getAppContext()?.apply {
            startActivity(Intent(this, ManualActivity::class.java))
        }
    }

    override fun showWorkForUs() {
        mView?.getAppContext()?.apply {
            startActivity(Intent(this, WorkForUsActivity::class.java))
        }
    }

    override fun showFaq() {
        mView?.getAppContext()?.apply {
            startActivity(Intent(this, FaqListActivity::class.java))
        }
    }


    override fun shareLinkOnSite(activity: Activity) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.facebook.com/entreGOya/")
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this service!")
        activity.startActivity(sendIntent)
    }

    //Panama city
    val mDefaultLocation = LatLng(9.047261, -79.484000)
    var mView: IRootView? = null
    override fun onCreate(view: IRootView) {
        mView = view
        mView?.requestPermissions(mPermissionListener)

    }

    val mPermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            mView?.onBuildMap()
        }
        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {

        }
    }

    override fun onStart() {
    }

    override fun onMapReady() {
        mView?.moveCamera(mDefaultLocation, false)
        mView?.showWelcomeBuilder()
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        mView = null
    }


    override fun showConditions() {
        mView?.getAppContext()?.apply {
            val title = getString(R.string.user_conditions_title)
            val message = getString(R.string.user_conditions_text)
            startActivity(DescriptionWithTopicActivity.getIntent(this, title, message))
        }
    }

    override fun showAboutEntrego() {
        mView?.getAppContext()?.apply {
            startActivity(Intent(this, AboutEntregoActivity::class.java))
        }
    }

    override fun showContactUs() {
        mView?.getAppContext()?.apply {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }
    }
}
