package com.fooin.android.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.fooin.android.R
import com.fooin.android.base.BaseActivity
import com.fooin.android.databinding.ActivityMainBinding
import com.fooin.android.model.NaverItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import dagger.hilt.android.AndroidEntryPoint
import ted.gun0912.clustering.naver.TedNaverClustering

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    MainViewModel::class.java
), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var naverClustering: TedNaverClustering<NaverItem>

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
        viewModel.markGeoEvent.observe(this) { address ->
            naverClustering.clearItems()
            naverClustering.addItem(
                NaverItem(
                    address.y.toDouble(),
                    address.x.toDouble(),
                )
            )

            val cameraUpdate =
                CameraUpdate.scrollTo(LatLng(address.y.toDouble(), address.x.toDouble()))
            naverMap.moveCamera(cameraUpdate)

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
        naverClustering = TedNaverClustering.with<NaverItem>(this, naverMap)
            .customMarker { clusterItem ->
                Marker(clusterItem.position).apply {
                    icon = MarkerIcons.GRAY
                    title = clusterItem.position.latitude.toString()
                }

            }
            .customCluster {
                TextView(this).apply {
                    setBackgroundColor(Color.DKGRAY)
                    setTextColor(Color.WHITE)
                    text = "${it.size} 개"
                    setPadding(10, 10, 10, 10)
                }
            }
            .items(getItems())
            .make()
    }

    // test items
    private fun getItems(): List<NaverItem> {
        val bounds = naverMap.contentBounds
        return ArrayList<NaverItem>().apply {
            repeat(50) {
                val temp = NaverItem(
                    (bounds.northLatitude - bounds.southLatitude) * Math.random() + bounds.southLatitude,
                    (bounds.eastLongitude - bounds.westLongitude) * Math.random() + bounds.westLongitude,
                )
                add(temp)
            }
        }

    }

}