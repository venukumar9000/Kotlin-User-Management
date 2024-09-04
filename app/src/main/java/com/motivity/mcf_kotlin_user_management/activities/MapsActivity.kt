package com.motivity.mcf_kotlin_user_management.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection
import com.motivity.mcf_kotlin_user_management.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    //private var currentLocationMarker: Marker? = null
    private var sourceLocation: LatLng? = null
    private var destinationLocation: LatLng? = null

    private lateinit var sourceAutocompleteFragment: AutocompleteSupportFragment
    private lateinit var destinationAutocompleteFragment: AutocompleteSupportFragment
    private var sourceMarker: Marker? = null
    private var destinationMarker: Marker? = null
    private var polyline: Polyline? = null

    // Request code for location permission
    private val LOCATION_PERMISSION_REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


        // Initialize Places API
        Places.initialize(applicationContext, getString(R.string.google_map_api_key))


        // Initialize AutocompleteSupportFragments
        sourceAutocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.source_autocomplete_fragment) as AutocompleteSupportFragment
        destinationAutocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.destination_autocomplete_fragment) as AutocompleteSupportFragment

        // Set place fields for AutocompleteSupportFragments
        sourceAutocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME)).setHint("Source")
        destinationAutocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME)).setHint("Destination")

        // Set place selection listeners for AutocompleteSupportFragments
        // Set place selection listeners for AutocompleteSupportFragments
        sourceAutocompleteFragment.setOnPlaceSelectedListener(object :
            PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                val sourcelocation = p0.name;
                formingPolyLine(true,p0)
            }             override fun onError(status: Status) {
                Log.e("PlaceSelectionListener", "Error processing selected place: ${status.statusMessage}")
                Toast.makeText(this@MapsActivity, "Error processing selected place", Toast.LENGTH_SHORT).show()
            }
        })

        destinationAutocompleteFragment.setOnPlaceSelectedListener(object :
            PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                val destinationLocation = p0.name;
                formingPolyLine(false,p0)
            }             override fun onError(status: Status) {
                Log.e("PlaceSelectionListener", "Error processing selected place: ${status.statusMessage}")
                Toast.makeText(this@MapsActivity, "Error processing selected place", Toast.LENGTH_SHORT).show()
            }
        })




        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize Map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val showCurrentLocationButton: Button = findViewById(R.id.showCurrentLocationButton)
        showCurrentLocationButton.setOnClickListener { showCurrentLocation() }
        // Initialize the "Start" button
        val startNavigationButton: Button = findViewById(R.id.startNavigationButton)
        startNavigationButton.setOnClickListener { startNavigation() }


        // Initialize Map Options
        val mapOptionButton: ImageButton = findViewById(R.id.mapOptionsMenu)
        val popupMenu = PopupMenu(this, mapOptionButton)
        popupMenu.menuInflater.inflate(R.menu.map_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMapType(menuItem.itemId)
            true
        }
        mapOptionButton.setOnClickListener {
            popupMenu.show()
        }
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(this, UserLoginActivity::class.java))
                    true
                }

                R.id.bottom_maps -> {
                    // MapsActivity
                    startActivity(Intent(this, MapsActivity::class.java))
                    true
                }
                R.id.bottom_search -> {
                    // MapsActivity
                    startActivity(Intent(this, UserRegistrationActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    private fun startNavigation() {
        if (sourceLocation != null && destinationLocation != null) {
            val uri = "http://maps.google.com/maps?saddr=${sourceLocation?.latitude},${sourceLocation?.longitude}" +
                    "&daddr=${destinationLocation?.latitude},${destinationLocation?.longitude}"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please select both source and destination", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeMapType(itemId: Int) {
        mGoogleMap?.let {
            when (itemId) {
                R.id.normal_map -> it.mapType = GoogleMap.MAP_TYPE_NORMAL
                R.id.hybrid_map -> it.mapType = GoogleMap.MAP_TYPE_HYBRID
                R.id.satellite_map -> it.mapType = GoogleMap.MAP_TYPE_SATELLITE
                R.id.terrain_map -> it.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
        } ?: Log.e("MapError", "GoogleMap is null")
    }
    private fun formingPolyLine(current : Boolean, place : Place) {
        // clearMarkersAndPolyline()
        var isUpdatingSource = current
        polyline?.remove()
        if (isUpdatingSource) {
            removingSourceMarkers()
            // Update source marker
            sourceMarker = mGoogleMap?.addMarker(
                MarkerOptions()
                    .position(place.latLng)
                    .title("Source")
                    .draggable(true)
            )
            sourceLocation = place.latLng
            if (destinationLocation != null) {
                drawPolyline(sourceLocation,destinationLocation);
            }
            // source = null;
            // flag = false;
        } else {
            removingDestinationMarkers();
            // Update destination marker
            destinationMarker = mGoogleMap?.addMarker(
                MarkerOptions()
                    .position(place.latLng)
                    .title("Destination")
                    .draggable(true)
            )

            destinationLocation = place.latLng
            drawPolyline(sourceLocation, destinationLocation)

            findViewById<Button>(R.id.startNavigationButton)?.isEnabled = true
            // flag = true
        }

        // Toggle the flag for the next update
        // isUpdatingSource = flag

        zoomOnMap(place.latLng)

    }



    private fun zoomOnMap(latLng: LatLng) {
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(latLng, 12f)
        mGoogleMap?.animateCamera(newLatLngZoom)
    }

    private fun drawPolyline(start: LatLng?, end: LatLng?) {
        start?.let { source ->
            end?.let { destination ->
                // Remove previous polyline
                polyline?.remove()

                // Fetch directions and draw new polyline
                FetchDirectionsTask(this).execute(source, destination)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        Log.d("MapReady", "Map is ready!")
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap?.isMyLocationEnabled = true
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

    }
    // Add this method to update the current location
    private fun showCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                        mGoogleMap?.addMarker(MarkerOptions().position(currentLocation).title("Your Location"))
                    }
                }
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, show the current location when the button is clicked
                showCurrentLocation()
            }
        }
    }

    private class FetchDirectionsTask(activity: MapsActivity) :
        AsyncTask<LatLng, Void, String>() {

        private val activityReference: WeakReference<MapsActivity> = WeakReference(activity)

        override fun doInBackground(vararg params: LatLng): String {
            val origin = params[0]
            val destination = params[1]

            val apiKey = activityReference.get()?.getString(R.string.google_map_api_key)

            val `url` = "https://maps.googleapis.com/maps/api/directions/json" +
                    "?origin=${origin.latitude},${origin.longitude}" +
                    "&destination=${destination.latitude},${destination.longitude}" +
                    "&key=$apiKey"

            return try {
                val connection = URL(url).openConnection() as HttpsURLConnection
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val stringBuilder = StringBuilder()

                    var line: String? = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = reader.readLine()
                    }

                    stringBuilder.toString()
                } else {
                    "Error: ${connection.responseMessage}"
                }
            } catch (e: Exception) {
                "Error: ${e.message}"
            }
        }

        override fun onPostExecute(result: String) {
            val activity = activityReference.get()
            activity?.let {
                // Parse JSON result and draw polyline
                val points = parseDirectionsResult(result)
                it.polyline = it.mGoogleMap?.addPolyline(
                    PolylineOptions()
                        .addAll(points)
                        .width(10f)
                        .color(Color.BLUE)
                )
            }
        }

        private fun parseDirectionsResult(result: String): List<LatLng> {
            val points = mutableListOf<LatLng>()

            try {
                val jsonObject = JSONObject(result)
                val routes = jsonObject.getJSONArray("routes")

                if (routes.length() > 0) {
                    val legs = routes.getJSONObject(0).getJSONArray("legs")
                    val steps = legs.getJSONObject(0).getJSONArray("steps")

                    for (i in 0 until steps.length()) {
                        val polyline =
                            steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                        points.addAll(decodePolyline(polyline))
                    }
                }
            } catch (e: Exception) {
                Log.e("DirectionsTask", "Error parsing directions result: ${e.message}")
            }

            return points
        }

        private fun decodePolyline(encoded: String): List<LatLng> {
            val poly = mutableListOf<LatLng>()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0

            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0

                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)

                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dlat

                shift = 0
                result = 0

                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)

                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dlng

                val p = LatLng(lat / 1E5, lng / 1E5)
                poly.add(p)
            }

            return poly
        }
    }

    private fun clearMarkersAndPolyline() {
        sourceMarker?.remove()
        destinationMarker?.remove()
        polyline?.remove()

        sourceMarker = null
        destinationMarker = null
        polyline = null

        // Disable the "Start" button
        findViewById<Button>(R.id.startNavigationButton)?.isEnabled = false
    }
    private fun removingDestinationMarkers() {
        if (destinationMarker != null) {
            destinationMarker?.remove()
        }

    }

    private fun removingSourceMarkers() {
        if (sourceMarker != null) {
            sourceMarker?.remove()
        }
    }

}

