package com.fooin.android.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.fooin.android.R
import com.fooin.android.base.BaseActivity
import com.fooin.android.databinding.ActivityMainBinding
import com.fooin.android.model.Position
import com.fooin.android.ui.dialog.StoreDetailDialogFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap

    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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

            val firstPosition =
                Position(
                    restaurants[0].positions[0].latitude,
                    restaurants[0].positions[0].longitude
                )
            restaurants.forEach { restaurant ->
                restaurant.positions.forEach { position ->
                    val marker = Marker()
                    setMarker(marker, position.latitude, position.longitude)
                    marker.setOnClickListener {
                        StoreDetailDialogFragment.newInstance(restaurant).apply {
                            show(
                                supportFragmentManager,
                                StoreDetailDialogFragment::class.java.simpleName
                            )
                        }
                        true
                    }
                }
            }


            val cameraUpdate =
                CameraUpdate.scrollTo(LatLng(firstPosition.latitude, firstPosition.longitude))
            naverMap.moveCamera(cameraUpdate)
        }
        viewModel.hideKeyboardEvent.observe(this) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.moveCamera(
            CameraUpdate.toCameraPosition(
                CameraPosition(
                    NaverMap.DEFAULT_CAMERA_POSITION.target,
                    NaverMap.DEFAULT_CAMERA_POSITION.zoom
                )
            )
        )
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

}