package com.example.apteki.ui.map

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
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
import com.example.apteki.yandex.Yandex
import com.example.apteki.yandex.YandexApi
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
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateSource
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapFragment : Fragment(), Map.CameraCallback,
    CameraListener {

    private lateinit var _binding: FragmentMapBinding
    private val binding get() = _binding
    private lateinit var userLocationLayer: UserLocationLayer
    private lateinit var currentLocation: Location
    private var isMapLoadedSuccessfully = false
    private var isMoveFinished = true
    private lateinit var streetHouseName: String
    private lateinit var completeAddress: String
    private lateinit var cityName: String

    private var isLocationOn = false
    private val BASE_URL = "https://geocode-maps.yandex.ru//1.x/"
    var call: Call<Yandex>? = null

    private val viewModel: SharedViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MapKitFactory.setApiKey("d2e07e8e-8462-4335-b333-141338e8578e")
//        MapKitFactory.setApiKey("6f742b73-07c2-4391-b56c-936e1ed66f17")
        MapKitFactory.initialize(context)
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
            // All loc_pin settings are satisfied. The client can initialize
            // loc_pin requests here.
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
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    listenForLocation()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.warning_enable_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
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
        if (call != null) {
            call?.cancel()
        }
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
        cameraUpdateSource: CameraUpdateSource,
        b: Boolean
    ) {
        try {
            if (b) {
                /*getDataFromUrl(
                    cameraPosition.target.latitude.toString(),
                    cameraPosition.target.longitude.toString()
                )*/
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

    /*   private fun getDataFromUrl(latitude: String, longitude: String) {

           val retrofit = Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()


           val yandexApi = retrofit.create(YandexApi::class.java)
           var sub_url =
               "?apikey=d2216fa4-de87-4d69-a2d6-8c0fc26f405b&format=json&geocode=$longitude,$latitude&results=1&lang=ru_RU"
   //            "?apikey=814c74ce-2250-497f-9bee-c9b376d64431&format=json&geocode=$longitude,$latitude&results=1&lang=ru_RU"

           call = yandexApi.getData(sub_url)

           call?.enqueue(object : Callback<Yandex?> {
               override fun onResponse(
                   call: Call<Yandex?>,
                   response: Response<Yandex?>
               ) {
                   Log.d("succes", "${response.isSuccessful}")
                   if (response.isSuccessful && response.body() != null) {
                       Log.d("succes", "here in success")
                       completeAddress = ""
                       streetHouseName = ""
                       cityName = ""
                       completeAddress = response.body()!!.response.geoObjectCollection
                           .featureMember[0].geoObject.metaDataProperty
                           .geocoderMetaData
                           .address.formatted
                       if (binding.cityText != null)
                           fillCityText()

                   } else {
                   }
               }

               override fun onFailure(
                   call: Call<Yandex?>,
                   t: Throwable
               ) {
               }
           })


       }*/

/*    private fun fillCityText() {
        Log.d("succes", "here in success in in in ")
        val completeAddressParser =
            completeAddress.split(",".toRegex()).toTypedArray()
        for (i in completeAddressParser.indices) {
            if (completeAddressParser.size >= 3) {
                streetHouseName =
                    completeAddressParser[completeAddressParser.size - 2] + ", " + completeAddressParser[completeAddressParser.size - 1]
                cityName = completeAddressParser[completeAddressParser.size - 3]
            }
        }
        if (streetHouseName != "") {
            binding.streetHouseText.text = streetHouseName.trim()
            binding.cityText.visibility = View.VISIBLE
            binding.cityText.text = cityName.trim()

        } else {
            binding.streetHouseText.text =
                resources.getString(R.string.street_house)
            binding.streetHouseText.setTextColor(Color.parseColor("#b6b6b6"))
            binding.cityText.visibility = View.VISIBLE
            binding.cityText.text =
                resources.getString(R.string.get_location_error)
        }
        Log.d("maper", "here 4")
        binding.btnChooseLocation.isEnabled = true
    }*/

}