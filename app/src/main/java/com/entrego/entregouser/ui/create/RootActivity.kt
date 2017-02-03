package com.entrego.entregouser.ui.create

import android.Manifest
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.auth.AuthActivity
import com.entrego.entregouser.ui.create.mvp.model.FragmentType
import com.entrego.entregouser.ui.create.mvp.presenter.IRootPresenter
import com.entrego.entregouser.ui.create.mvp.presenter.RootPresenter
import com.entrego.entregouser.ui.create.mvp.view.IRootView
import com.entrego.entregouser.ui.create.mvp.view.RootActivityController
import com.entrego.entregouser.ui.create.steps.accept.AcceptDeliveryCreationFragment
import com.entrego.entregouser.ui.create.steps.service.SelectServiceFragment
import com.entrego.entregouser.ui.create.steps.types.deliver.DeliverTypesFragment
import com.entrego.entregouser.ui.create.steps.types.shipment.ShipmentTypesFragment
import com.entrego.entregouser.ui.create.steps.types.transaction.TransactionTypesFragment
import com.entrego.entregouser.ui.profile.edit.EditProfileActivity
import com.entrego.entregouser.util.showSnack
import com.entrego.entregouser.web.model.response.delivery.create.DeliveryCreationResponse
import com.facebook.internal.Utility.logd
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.app_bar_root.*
import kotlinx.android.synthetic.main.container_drawer.*
import kotlinx.android.synthetic.main.content_root.*

class RootActivity : AppCompatActivity(), OnMapReadyCallback, IRootView, RootActivityController {
    companion object {
        val TAG = "RootActivity"
    }

    private var mMap: GoogleMap? = null
    protected val mPresenter: IRootPresenter = RootPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setSupportActionBar(root_toolbar)
        supportActionBar?.title = ""
        val drawer = findViewById(R.id.activity_root_drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this,
                drawer,
                root_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        setupTabIcons()
        setupListeners()
        mPresenter.onCreate(this)
    }

    fun setupListeners() {
        root_drawer_container.setOnClickListener { }
        drawer_edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
        tab_fl_shipment.setOnClickListener { showTypeShipmentFragment() }
        tab_fl_deliver.setOnClickListener { showTypeDeliverFragment() }
        tab_fl_transaction.setOnClickListener { showTypeTransactionFragment() }
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


    fun setupTabIcons() {
        val tabTitles = resources.getStringArray(R.array.tab_titles)
        val tabIcons = arrayOf<Int>(R.drawable.ic_box,
                R.drawable.ic_deliver,
                R.drawable.ic_transaction)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings
                .isMyLocationButtonEnabled = true
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
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onBuildMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
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

    override fun showTypeShipmentFragment() {
        val fragment = ShipmentTypesFragment()
        showBuilderFragment(fragment, FragmentType.PARAMETERS)
    }

    override fun showMessage(stringId: Int) {
        activity_root_drawer_layout?.showSnack(getString(stringId))
    }

    override fun showTypeDeliverFragment() {
        showBuilderFragment(DeliverTypesFragment(), FragmentType.PARAMETERS)
    }

    override fun showTypeTransactionFragment() {
        showBuilderFragment(TransactionTypesFragment(), FragmentType.PARAMETERS)
    }

    override fun showBuilderFragment(fragment: Fragment, type: FragmentType) {

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

    override fun showAcceptDeliveryCreationFragment(model: DeliveryCreationResponse) {
        val fragment = AcceptDeliveryCreationFragment.getInstance(model)
        fragmentManager.beginTransaction()
                .replace(R.id.root_front_container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun showCreatedDelivery() {

        startActivity(Intent(this, AuthActivity::class.java))
    }


}