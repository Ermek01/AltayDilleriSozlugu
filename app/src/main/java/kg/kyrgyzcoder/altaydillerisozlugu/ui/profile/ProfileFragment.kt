package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.gms.location.*
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelProfileUser
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentMainBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentProfileBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.LogoutFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.ProfileListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.util.OnBackPressed
import kg.kyrgyzcoder.altaydillerisozlugu.util.hide
import kg.kyrgyzcoder.altaydillerisozlugu.util.show
import kg.kyrgyzcoder.altaydillerisozlugu.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class ProfileFragment : Fragment(), KodeinAware, ProfileListener {

    override val kodein: Kodein by closestKodein()
    private val profileViewModelFactory: ProfileViewModelFactory by instance()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    private lateinit var profileViewModel: ProfileViewModel

    var PERMISION_ID = 52

    private var token: String? = "token"

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProvider(
            requireActivity(),
            profileViewModelFactory
        ).get(ProfileViewModel::class.java)

        profileViewModel.setProfileListener(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        profileViewModel.userToken.asLiveData().observe(viewLifecycleOwner, {
            token = it
            if (token!!.isEmpty()) {
                binding.btnLogout.visibility = View.GONE
                binding.cardView.visibility = View.VISIBLE
                binding.edit.visibility = View.GONE
            } else {
                binding.prBar.show()
                getLastLocation()
                profileViewModel.profileUser()
                binding.layoutBasicMember.visibility = View.VISIBLE
            }
        })



        binding.btnLogout.setOnClickListener {

            val fm = fragmentManager
            val logoutFragment = LogoutFragment()
            logoutFragment.show(fm!!, "")

        }
    }

    override fun getProfileSuccess(modelProfileUser: ModelProfileUser) {
        binding.txtUsername.text = modelProfileUser.username
        binding.txtEmail.text = modelProfileUser.email
        binding.prBar.hide()
    }

    override fun getProfileFailure(code: Int?) {
        requireActivity().toast(getString(R.string.txt_error_request))
    }

    private fun getLastLocation() {
        if (checkPermission()){
            if (isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        getNewLocation()
                    }
                    else {
                        binding.txtCountry.text = getCountryName(location.latitude, location.longitude)
                    }
                }
            }
            else {
                requireActivity().toast(getString(R.string.txt_enable_geo))
            }
        }
        else {
            requestPermission()
        }

    }

    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation: Location = p0.lastLocation
            binding.txtCountry.text = getCountryName(lastLocation.latitude, lastLocation.longitude)
        }
    }

    private fun checkPermission(): Boolean {

        if (
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            return true
        }

        return false
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISION_ID)
    }

    private fun isLocationEnabled() : Boolean {

        var locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getCountryName(lat: Double, long: Double) : String {

        var countryName = ""
        var geoCoder = Geocoder(requireContext(), Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)

        countryName = address.get(0).countryName
        return countryName
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }

}