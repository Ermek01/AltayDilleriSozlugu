package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelProfileUser
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentProfileBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.LogoutFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.ProfileListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.SplashScreenActivity
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.LanguageListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.SelectLanguageFragment
import kg.kyrgyzcoder.altaydillerisozlugu.util.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class ProfileFragment : Fragment(), KodeinAware, ProfileListener, LanguageListener {

    private var mFlag = 0
    private var flag: Int = 0
    private var nameCountry = ""
    private var code: String? = ""
    private var name: String? = ""
    private var mCode = ""

    override val kodein: Kodein by closestKodein()
    private val profileViewModelFactory: ProfileViewModelFactory by instance()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    private lateinit var profileViewModel: ProfileViewModel

    var PERMISION_ID = 52

    private var token: String? = "token"

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    override fun getLanguage(flag: Int, name: String, code: String) {
        mFlag = flag
        nameCountry = name
        mCode = code
        val pref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putInt(FLAG_KEY, mFlag)
        editor.putString(CODE_KEY, mCode)
        editor.putString(NAME_KEY, nameCountry)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLanguage()
    }

    private fun loadLanguage() {
        val pref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        flag = pref.getInt(FLAG_KEY, 0)
        code = pref.getString(CODE_KEY, "")
        name = pref.getString(NAME_KEY, "")
        val language = pref.getString(LANGUAGE_KEY, "")


        val locale = Locale(code!!)
        Locale.setDefault(locale)
        val config = Configuration()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locale = locale
            requireActivity().baseContext.resources.updateConfiguration(
                config,
                requireActivity().baseContext.resources.displayMetrics
            )
        } else {
            config.setLocale(locale)
        }

        requireActivity().applicationContext.createConfigurationContext(config)
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

        if (flag != 0) {
            binding.imgFlag.setImageResource(flag)
            binding.etLanguage.text = name
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        profileViewModel.userToken.asLiveData().observe(viewLifecycleOwner, {
            token = it
            if (token == null || token!!.isEmpty()) {
                binding.btnLogout.visibility = View.GONE
                binding.cardView.visibility = View.VISIBLE
                binding.edit.visibility = View.GONE
                binding.toolbar.visibility = View.GONE
                val layoutParams = binding.mainIcon.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
                binding.mainIcon.layoutParams = layoutParams

            } else {
                binding.prBar.show()
                getLastLocation()
                profileViewModel.profileUser()
                binding.layoutBasicMember.visibility = View.VISIBLE
            }
        })

        binding.language.setOnClickListener {
            val fm = fragmentManager
            val logoutFragment = SelectLanguageFragment(this)
            logoutFragment.show(fm!!, "")
        }

        binding.btnLogout.setOnClickListener {

            val fm = fragmentManager
            val logoutFragment = LogoutFragment()
            logoutFragment.show(fm!!, "")

        }

        binding.register.setOnClickListener {
            val intent = Intent(requireContext(), SplashScreenActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.edit.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToProfileEditFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    override fun getProfileSuccess(modelProfileUser: ModelProfileUser) {

        try {
            Glide.with(binding.root).load(modelProfileUser.image)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_bg_profile_img))
                .into(binding.profileImg)
            binding.txtUsername.text = modelProfileUser.username
            binding.txtEmail.text = modelProfileUser.email
            binding.prBar.hide()
        }catch (e: Exception) {
            Log.e("ololo", e.toString())
        }


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