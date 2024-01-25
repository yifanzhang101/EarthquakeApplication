package com.example.earthquakeapplication.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.earthquakeapplication.R
import com.example.earthquakeapplication.model.EarthquakeGeometry
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var locationTextView: TextView
    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private var earthquakeGeometry: EarthquakeGeometry? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        locationTextView = findViewById(R.id.locationTextView)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        earthquakeGeometry = intent.getParcelableExtra("earthquakeGeometry")
        earthquakeGeometry?.let {
            val latitude = it.coordinates[1]
            val longitude = it.coordinates[0]
            locationTextView.text = "Lat: ${latitude}, Lng: ${longitude}"
            locationTextView.setOnClickListener {
                openGoogleMaps(latitude, longitude)
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        earthquakeGeometry?.let {
            val location = LatLng(it.coordinates[1], it.coordinates[0])
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
            googleMap?.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Earthquake Location")
            )
        }
    }

    // Ensure to call these methods to handle the lifecycle of the MapView
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun openGoogleMaps(latitude: Double, longitude: Double) {
        val geoUri = "geo:$latitude,$longitude?q=$latitude,$longitude"
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // If Google Maps app is not available, you can open the Maps website
            val mapUrl = "http://maps.google.com/maps?q=$latitude,$longitude"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
            startActivity(browserIntent)
        }
    }
}
