package com.entrego.entregouser.ui.main

import android.Manifest
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatDrawableManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.entrego.entregouser.R
import com.entrego.entregouser.ui.main.mvp.presenter.IRootPresenter
import com.entrego.entregouser.ui.main.mvp.presenter.RootPresenter
import com.entrego.entregouser.ui.main.mvp.view.IRootView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.app_bar_root.*
import kotlinx.android.synthetic.main.container_drawer.*
import kotlinx.android.synthetic.main.content_root.*

class RootActivity : AppCompatActivity(), OnMapReadyCallback, IRootView {

    private var mMap: GoogleMap? = null
    protected val mPresenter: IRootPresenter = RootPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setSupportActionBar(root_toolbar)
        supportActionBar?.title = ""
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this,
                drawer,
                root_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        setupTabIcons()
        root_drawer_container.setOnClickListener { }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    fun setupTabIcons() {
        val tabTitles = resources.getStringArray(R.array.tab_titles)
        val tabIcons = arrayOf<Int>(R.drawable.ic_box,
                R.drawable.ic_deliver,
                R.drawable.ic_transaction)

        for (i in 0..tabTitles.lastIndex) {
            main_tabs.addTab(main_tabs.newTab())
            val nextOne = LayoutInflater.from(this).inflate(R.layout.tab_view, null)
            (nextOne.findViewById(R.id.tab_text) as TextView).text = tabTitles[i]
            val icon = AppCompatDrawableManager.get().getDrawable(applicationContext, tabIcons[i])
            (nextOne.findViewById(R.id.tab_icon) as ImageView).setImageDrawable(icon)
            main_tabs.getTabAt(i)?.customView = nextOne
        }
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
}
