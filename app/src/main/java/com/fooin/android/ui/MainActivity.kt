package com.fooin.android.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import com.fooin.android.R
import com.fooin.android.base.BaseActivity
import com.fooin.android.databinding.ActivityMainBinding
import com.fooin.android.ui.dialog.RestaurantDetailDialogFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap

    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val locationSource by lazy {
        FusedLocationSource(this, REQ_CODE_LOCATION_PERMISSION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        setupObserve()
    }

    private fun setupUi() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fl_map) as MapFragment?
            ?: run {
                MapFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().add(R.id.fl_map, it).commit()
                }
            }
        mapFragment.getMapAsync(this)
    }

    private fun setupObserve() {
        viewModel.restaurantItems.observe(this) { restaurants ->
            if (restaurants.isEmpty()) return@observe

            restaurants.forEach { restaurant ->
                val marker = Marker()
                setMarker(marker, restaurant.latitude, restaurant.longitude)
                marker.setOnClickListener {
                    RestaurantDetailDialogFragment.newInstance(restaurant).apply {
                        show(
                            supportFragmentManager,
                            RestaurantDetailDialogFragment::class.java.simpleName
                        )
                    }
                    true
                }
            }
        }
        viewModel.hideKeyboardEvent.observe(this) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        this.naverMap.locationSource = locationSource
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQ_CODE_LOCATION_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_CODE_LOCATION_PERMISSION && grantResults.isNotEmpty()) {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }
    }

    private fun setMarker(
        marker: Marker,
        lat: Double,
        lng: Double,
    ) {
        marker.isIconPerspectiveEnabled = true
        marker.icon = OverlayImage.fromResource(R.drawable.ic_location_on)
        marker.alpha = 0.8f
        marker.position = LatLng(lat, lng)
        marker.zIndex = 0
        marker.map = naverMap
    }

    companion object {
        private const val REQ_CODE_LOCATION_PERMISSION = 1001
    }

}