package com.example.apteki.ui.map

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.apteki.R
import com.example.apteki.databinding.FragmentMapBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.user_location.UserLocationLayer
import org.koin.android.ext.android.inject
import retrofit2.Call


class MapFragment : Fragment(), Map.CameraCallback,
    CameraListener {

    private lateinit var _binding: FragmentMapBinding
    private val binding get() = _binding
    private lateinit var userLocationLayer: UserLocationLayer
    private lateinit var currentLocation: Location
    private var isMapLoadedSuccessfully = false
    private var isMoveFinished = true

    private var isLocationOn = false
    private val BASE_URL = "https://geocode-maps.yandex.ru//1.x/"

    private val viewModel: SharedViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("d2e07e8e-8462-4335-b333-141338e8578e")
//        MapKitFactory.setApiKey("6f742b73-07c2-4391-b56c-936e1ed66f17")
        MapKitFactory.initialize(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentMapBinding.inflate(inflater, container, false)

        askForLocationPermission()

        val alignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
        binding.mapView.map.logo.setAlignment(alignment)

        binding.findMe.setOnClickListener {
            if (isMapLoadedSuccessfully && checkIfGpsEnabled()) {
                binding.mapView.map.move(
                    CameraPosition(
                        currentLocation.position,
                        binding.mapView.map.maxZoom, 10.0f, 0.0f
                    ),
                    Animation(Animation.Type.SMOOTH, 1F),
                    null
                );
            } else {
                askForLocationPermission()
            }
        }

        binding.mapView.map.move(
            CameraPosition(Point(41.311081, 69.240562), binding.mapView.map.maxZoom, 10.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2f),
            null
        )

        binding.mapView.map.addCameraListener(this)


        binding.btnChooseLocation.setOnClickListener {
            val currentPoint = binding.mapView.map.cameraPosition.target
            val latitude = currentPoint.latitude
            val longitude = currentPoint.longitude
            val arguments = MapArguments(
                lat = latitude,
                lng = longitude
            )
            viewModel.mapArguments.postValue(arguments)
            viewModel.firstUse = true
            viewModel.continueEditing = true

            findNavController().popBackStack()
        }

        return binding.root
    }


    private fun askForLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1020
            )
        } else {
            listenForLocation()
        }
    }

    private fun checkIfGpsEnabled(): Boolean {
        val lm: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun requestLocationSettingsOn() {
        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            .addLocationRequest(createLocationRequest()!!)

        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            isLocationOn = true
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {

                    exception.startResolutionForResult(
                        requireActivity(),
                        1001
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.warning_enable_location),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    private fun createLocationRequest(): LocationRequest? {
        return LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            1020 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

                    listenForLocation()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.warning_enable_permission),
                        Toast.LENGTH_SHORT
                    ).show()

                }
                return
            }


            else -> {
            }
        }

    }


    private fun listenForLocation() {
        if (!checkIfGpsEnabled()) {
            requestLocationSettingsOn()
        } else {
            MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(object :
                LocationListener {
                override fun onLocationUpdated(location: Location) {
                    currentLocation = location
                    isMapLoadedSuccessfully = true
                    if (isMapLoadedSuccessfully && checkIfGpsEnabled()) {
                        binding.mapView.map.move(
                            CameraPosition(
                                currentLocation.position,
                                binding.mapView.map.maxZoom, 10.0f, 0.0f
                            ),
                            Animation(Animation.Type.SMOOTH, 1F),
                            null
                        );
                    } else {
                        askForLocationPermission()
                    }
                }

                override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
                    Log.d("TagCheck", locationStatus.name)

                }
            })
        }
    }


    override fun onStart() {
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }


    override fun onStop() {

        super.onStop()
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onMoveFinished(finished: Boolean) {
        if (finished) {
            userLocationLayer.cameraPosition()?.target?.let {
                CameraPosition(
                    it,
                    binding.mapView.map.maxZoom, 10.0f, 0.0f
                )
            }?.let { binding.mapView.map.move(it) }

            isMoveFinished = true
        } else {
            binding.btnChooseLocation.isEnabled = false

        }
        TODO("Not yet implemented")
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        p2: CameraUpdateReason,
        b: Boolean
    ) {
        try {
            if (b) {

                Log.d(
                    "maper",
                    "2 here 3 ${cameraPosition.target.latitude} ${cameraPosition.target.longitude}"
                )
                binding.btnChooseLocation.isEnabled = true
            } else {
                binding.btnChooseLocation.isEnabled = false
            }
        } catch (ex: Exception) {
        }
    }





}