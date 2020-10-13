package com.example.googlemapsexamples.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowMetrics
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlemapsexamples.R
import com.example.googlemapsexamples.base.BaseActivity
import com.example.googlemapsexamples.home.adapter.MenuAdapter
import com.example.googlemapsexamples.home.menus.currenttofixedlocation.CurrentToFixedLocationActivity
import com.example.googlemapsexamples.home.menus.snapdirection.SnapDirectionActivity
import com.example.googlemapsexamples.home.model.MenuModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_google_map.view.*
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.content_menu.view.*
import kotlinx.android.synthetic.main.inflate_toolbar.view.*


class GoogleMapActivity : BaseActivity(), OnMapReadyCallback {

    private val MENU_CURRENT_LOCATION_TO_FIXED_LOCATION = 1001
    private val MENU_SNAP = 1002
    private var menuAdapter: MenuAdapter? = null
    private var clickedTime = 0L
    private var mapFragment: SupportMapFragment? = null
    private var mMap: GoogleMap? = null

    override fun setLayout(): Int {
        return R.layout.activity_nav
    }

    override fun initView(savedInstanceState: Bundle?) {
        setToolbar()
        setupNavigationView()
        setUpNavigationDrawer()
        setMenuAdapter()
        mapsInit()
    }

    private fun mapsInit() {
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun setupNavigationView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            (navView.layoutParams as? DrawerLayout.LayoutParams?)?.let {
                it.width = windowMetrics.bounds.width() - insets.left - insets.right
                navView.layoutParams
            }
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            (navView.layoutParams as? DrawerLayout.LayoutParams?)?.let {
                it.width = displayMetrics.widthPixels
                navView.layoutParams
            }
        }
    }

    private fun setMenuAdapter() {
        menuAdapter = MenuAdapter()
        val list = ArrayList<MenuModel>()
        list.add(
            MenuModel(
                MENU_CURRENT_LOCATION_TO_FIXED_LOCATION,
                R.string.icon_Logo_icon,
                getString(R.string.current_to_fixed)
            )
        )
        list.add(
            MenuModel(
                MENU_SNAP,
                R.string.icon_Play,
                getString(R.string.snap_direction)
            )
        )

        navView
            .recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        navView.recyclerViewMenu.adapter = menuAdapter
        menuAdapter?.addList(list)
        menuAdapter?.setOnMenuItemClickListener(object :
            MenuAdapter.MenuCommunicator {
            override fun menuClick(menuId: Int) {
                when (menuId) {
                    MENU_CURRENT_LOCATION_TO_FIXED_LOCATION -> {
                        if (isNetworkConnected()) {
                            startActivity(
                                Intent(
                                    this@GoogleMapActivity,
                                    CurrentToFixedLocationActivity::class.java
                                )
                            )
                        } else {
                            noInternetError()
                        }
                    }
                    MENU_SNAP -> {
                        if (isNetworkConnected()) {
                            startActivity(
                                Intent(
                                    this@GoogleMapActivity,
                                    SnapDirectionActivity::class.java
                                )
                            )
                        } else {
                            noInternetError()
                        }
                    }
                }
            }
        })
    }

    private fun setUpNavigationDrawer() {
        contentMain.googleMapsToolbar?.txtToolBarRight?.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }
        navView.imgClose.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    private fun setToolbar() {
        contentMain.googleMapsToolbar?.txtToolbarBack?.visibility = View.INVISIBLE
        contentMain.googleMapsToolbar?.txtToolBarRight?.visibility = View.VISIBLE
        contentMain.googleMapsToolbar?.txtToolbarBack?.text =
            getString(R.string.icon_left_arrow)
        contentMain.googleMapsToolbar?.txtToolBarRight?.text =
            getString(R.string.icon_menu)
        contentMain.googleMapsToolbar?.txtToolbarBack?.setOnClickListener {
            onBackPressed()
        }
        contentMain.googleMapsToolbar?.txtToolbarHeading?.text = "Google Map"
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        when {
            drawerLayout.isDrawerOpen(GravityCompat.END) -> {
                drawerLayout.closeDrawer(GravityCompat.END)
            }
            (currentTime - clickedTime) <= 1500 -> {
                super.onBackPressed()
            }
            else -> {
                clickedTime = currentTime
                showToast("Press Again to Exit")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        setMap(googleMap)
    }

    private fun setMap(googleMap: GoogleMap?) {
        googleMap?.uiSettings?.isMapToolbarEnabled = false
        mMap = googleMap
        mMap?.clear()
    }
}