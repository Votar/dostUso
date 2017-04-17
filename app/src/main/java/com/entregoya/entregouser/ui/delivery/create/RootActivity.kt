package com.entregoya.entregouser.ui.delivery.create

import android.Manifest
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.back.EntregoDeliveryPreview
import com.entregoya.entregouser.entity.delivery.DeliveryEntityBuilder
import com.entregoya.entregouser.entity.delivery.EntregoServiceCategory
import com.entregoya.entregouser.storage.EntregoStorage
import com.entregoya.entregouser.ui.delivery.create.mvp.model.FragmentType
import com.entregoya.entregouser.ui.delivery.create.mvp.presenter.IRootPresenter
import com.entregoya.entregouser.ui.delivery.create.mvp.presenter.RootPresenter
import com.entregoya.entregouser.ui.delivery.create.mvp.view.IRootView
import com.entregoya.entregouser.ui.delivery.create.mvp.view.RootActivityController
import com.entregoya.entregouser.ui.delivery.create.steps.BaseBuilderFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.category.deliver.DeliverBuyFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.category.transaction.TransactionTypesFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.dummy.SelectServiceFragment
import com.entregoya.entregouser.ui.delivery.create.steps.building.size.SelectSizeFragment
import com.entregoya.entregouser.ui.delivery.create.steps.confirmation.DeliveryConfirmationFragment
import com.entregoya.entregouser.ui.faq.FaqListActivity
import com.entregoya.entregouser.ui.favorites.FavoritesActivity
import com.entregoya.entregouser.ui.history.HistoryDeliveriesActivity
import com.entregoya.entregouser.ui.payment.PaymentMethodActivity
import com.entregoya.entregouser.ui.profile.edit.EditProfileActivity
import com.entregoya.entregouser.util.GsonHolder
import com.entregoya.entregouser.util.loadCustomerPicWithToken
import com.entregoya.entregouser.util.logout
import com.entregoya.entregouser.util.showSnack
import com.entregoya.entregouser.web.socket.SocketService
import com.facebook.internal.Utility.logd
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.app_bar_root.*
import kotlinx.android.synthetic.main.container_drawer.*
import kotlinx.android.synthetic.main.content_root.*

class RootActivity : AppCompatActivity(), OnMapReadyCallback, IRootView, RootActivityController {


    companion object {
        val TAG = "RootActivity"
        fun getIntent(ctx: Context): Intent {
            val intent = Intent(ctx, RootActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }

    private var mMap: GoogleMap? = null
    protected val mPresenter: IRootPresenter = RootPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setSupportActionBar(root_toolbar)
        mPresenter.onCreate(this)
        setupLayouts()
        setupListeners()
        val token = EntregoStorage.getTokenOrEmpty()
        startService(Intent(this, SocketService::class.java))
    }

    fun setupLayouts() {
        supportActionBar?.title = ""

        val toggle = ActionBarDrawerToggle(this,
                activity_root_drawer_layout,
                root_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        activity_root_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun setupListeners() {
        drawer_payment_method_bth.setOnClickListener { showPaymentMethods() }
        drawer_my_deliveries_btn.setOnClickListener { showMyDeliveries() }
        drawer_edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        tab_fl_shipment.setOnClickListener { showTypeShipmentFragment() }
        tab_fl_deliver.setOnClickListener { showTypeDeliverFragment() }
        tab_fl_transaction.setOnClickListener { showTypeTransactionFragment() }

        root_favorites_rl.setOnClickListener { showFavoritesActivity() }
        drawer_share_btn.setOnClickListener { mPresenter.shareLinkOnSite(RootActivity@ this) }
        drawer_conditions.setOnClickListener { mPresenter.showConditions() }
        drawer_about_entrego.setOnClickListener { mPresenter.showAboutEntrego() }
        drawaer_contact_us.setOnClickListener { mPresenter.showContactUs() }
        drawer_frequently_asked.setOnClickListener { mPresenter.showFaq() }
        drawer_work_for_us.setOnClickListener { mPresenter.showWorkForUs() }
        drawer_hire_delivery.setOnClickListener {
            activity_root_drawer_layout.closeDrawers()
        }
        drawer_logout.setOnClickListener { this.logout() }
        drawer_user_manual.setOnClickListener {
            mPresenter.showUserManual()
        }
        account_user_pic_holder.loadCustomerPicWithToken()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
    }

    private fun showMyDeliveries() {
        startActivity(HistoryDeliveriesActivity.getIntent(this))
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
        logd(TAG, "onStart")

    }

    override fun onRestart() {
        super.onRestart()
        logd(TAG, "onRestart")

    }

    override fun onPause() {
        super.onPause()
        logd(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
        logd(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.isMyLocationEnabled = true
        mMap = googleMap
        mPresenter.onMapReady()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.root, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_faq)
            startActivity(Intent(this, FaqListActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    override fun onBuildMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.root_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun requestPermissions(listener: PermissionListener) {
        TedPermission(this)
                .setPermissionListener(listener)
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check()
    }

    override fun moveCamera(position: LatLng, smooth: Boolean) {
        val nextCamera = CameraUpdateFactory
                .newLatLngZoom(position, 16f)
        mMap?.moveCamera(nextCamera)
    }

    override fun showWelcomeBuilder() {
        showBuilderFragment(SelectServiceFragment(), FragmentType.PARAMETERS)
    }


    override fun showMessage(stringId: Int) {
        activity_root_drawer_layout?.showSnack(getString(stringId))
    }

    override fun showTypeShipmentFragment() {
        val deliveryBuilder = DeliveryEntityBuilder()
        val firstFragment = SelectSizeFragment()
        deliveryBuilder.category = EntregoServiceCategory.SHIPMENT
        val args = Bundle()
        val gson = GsonHolder.instance
        args.putString(BaseBuilderFragment.KEY_BUILDER, gson.toJson(deliveryBuilder, DeliveryEntityBuilder::class.java))
        firstFragment.arguments = args
        showBuilderFragment(firstFragment, FragmentType.PARAMETERS)
    }

    override fun showTypeDeliverFragment() {
        showBuilderFragment(DeliverBuyFragment(), FragmentType.PARAMETERS)
    }

    override fun showTypeTransactionFragment() {
        showBuilderFragment(TransactionTypesFragment(), FragmentType.PARAMETERS)
    }

    override fun showBuilderFragment(fragment: Fragment, type: FragmentType) {
        if (mPresenter.isViewAvailable()) {
            when (type) {
                FragmentType.ADDRESS ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.root_builder_container, Fragment())
                            .replace(R.id.root_address_container, fragment)
                            .commit()
                FragmentType.PARAMETERS ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.root_address_container, Fragment())
                            .replace(R.id.root_builder_container, fragment)
                            .commit()
            }
        }
    }

    override fun showAcceptDeliveryCreationFragment(model: EntregoDeliveryPreview) {
        if (mPresenter.isViewAvailable()) {
            val fragment = DeliveryConfirmationFragment.getInstance(model)
            fragmentManager.beginTransaction()
                    .replace(R.id.root_front_container, fragment, DeliveryConfirmationFragment.TAG)
                    .addToBackStack(null)
                    .commit()
        }
    }


    override fun showCreatedDelivery(delivery: EntregoDeliveryPreview?) {
        if (mPresenter.isViewAvailable()) {
            showWelcomeBuilder()
            fragmentManager.findFragmentByTag(DeliveryConfirmationFragment.TAG)?.let {
                fragmentManager.beginTransaction()
                        .remove(it)
                        .commit()
            }
        }
    }

    override fun showPaymentMethods() {
        startActivity(PaymentMethodActivity.getIntent(this))
    }

    override fun getCurrentFocusOnMap(): LatLngBounds? = mMap?.projection?.visibleRegion?.latLngBounds

    override fun showFavoritesActivity() {
        startActivity(Intent(this, FavoritesActivity::class.java))
    }

    override fun getAppContext(): Context = this


}
