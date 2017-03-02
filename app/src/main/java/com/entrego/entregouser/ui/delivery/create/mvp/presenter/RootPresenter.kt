package com.entrego.entregouser.ui.delivery.create.mvp.presenter

import android.app.Activity
import android.content.Intent
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.comon.AboutEntregoActivity
import com.entrego.entregouser.ui.comon.ContactUsActivity
import com.entrego.entregouser.ui.comon.DescriptionWithTopicActivity
import com.entrego.entregouser.ui.delivery.create.mvp.view.IRootView
import com.entrego.entregouser.ui.faq.FaqListActivity
import com.entrego.entregouser.ui.profile.history.details.DetailsDeliveryActivity
import com.entrego.entregouser.ui.promo.WorkForUsActivity
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import java.util.*

class RootPresenter : IRootPresenter {
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.whatsapp.com/?l=ru")
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this site!");
        activity.startActivity(sendIntent)
    }

    var mView: IRootView? = null
    //Panama city
    val mDefaultLocation = LatLng(9.047261, -79.484000)
    val mPermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            mView?.onBuildMap()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            //
        }
    }

    override fun onCreate(view: IRootView) {
        mView = view
        mView?.requestPermissions(mPermissionListener)
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
