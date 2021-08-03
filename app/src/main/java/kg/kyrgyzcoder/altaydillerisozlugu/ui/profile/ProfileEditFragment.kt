    package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelEditProfile
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelProfileUser
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentProfileEditBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.EditProfileListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.ProfileListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.LanguageListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.SelectLanguageFragment
import kg.kyrgyzcoder.altaydillerisozlugu.util.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.*
import java.lang.Exception
import java.util.*


class ProfileEditFragment : Fragment(), KodeinAware, ProfileListener, LanguageListener, EditProfileListener{

    private var mFlag = 0
    private var flag: Int = 0
    private var nameCountry = ""
    private var code: String? = ""
    private var name: String? = ""
    private var mCode = ""
    private var image: String = ""
    private var file: File? = null
    var mUri: Uri? = null
    lateinit var byteArrayOutputStream: ByteArrayOutputStream
    lateinit var imageInByte: ByteArray
    var requestImage: MultipartBody.Part? = null
    var mUsername: RequestBody? = null

    override val kodein: Kodein by closestKodein()
    private val profileViewModelFactory: ProfileViewModelFactory by instance()
    private lateinit var profileViewModel: ProfileViewModel

    private var _binding: FragmentProfileEditBinding? = null
    private val binding: FragmentProfileEditBinding get() = _binding!!

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity().setAspectRatio(1,1).getIntent(requireActivity())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }

    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>


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
    ): View? {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
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


        profileViewModel.profileUser()
        profileViewModel.setEditProfileListener(this)
        binding.basicMember.visibility = View.VISIBLE

        binding.language.setOnClickListener {
            val fm = fragmentManager
            val logoutFragment = SelectLanguageFragment(this)
            logoutFragment.show(fm!!, "")
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack(R.id.profileEditFragment, true)
        }

        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
            it?.let { uri ->
                binding.profileImg.setImageURI(uri)

                if (file == null) {
                    file = fileFromContentUri(context!!, uri)
                }

                val requestFile : RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
                requestImage = MultipartBody.Part.createFormData("image", file!!.name, requestFile)

            }
        }

        binding.profileImg.setOnClickListener {
            cropActivityResultLauncher.launch(null)
        }

        binding.edit.setOnClickListener {
            val username = binding.etFullName.text.toString()
            mUsername = RequestBody.create("text/plain".toMediaTypeOrNull(), username)
            profileViewModel.editProfileUser(requestImage, mUsername!!)
        }
    }

    override fun getProfileSuccess(modelProfileUser: ModelProfileUser) {

        if (modelProfileUser.image == null) {
            binding.profileImg.setImageResource(R.drawable.ic_bg_profile_img)
        }
        else {
            Glide.with(binding.root).load(modelProfileUser.image)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_bg_profile_img))
                .into(binding.profileImg)
        }
        binding.etFullName.setText(modelProfileUser.username)
        binding.etEmail.setText(modelProfileUser.email)
    }

    override fun getProfileFailure(code: Int?) {
        requireActivity().toast(getString(R.string.txt_error_request))
    }

    override fun editProfileSuccess() {
        Navigation.findNavController(binding.root).popBackStack(R.id.profileEditFragment, true)
    }

    override fun editProfileFailure(code: Int?) {
        requireActivity().toast("Error $code")
    }

    fun fileFromContentUri(context: Context, uri: Uri?) : File {
        val fileExtension = getFileExtension(context, uri)
        val fileName = "temp_file.jpg" + if (fileExtension != null) ".$fileExtension" else ""

        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()


        try {
            val osStream = FileOutputStream(tempFile)
            val inputStream = uri?.let { context.contentResolver.openInputStream(it) }

            inputStream?.let {
                copy(inputStream, osStream)
            }
            osStream.flush()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        return tempFile
    }

    private fun getFileExtension(context: Context, uri: Uri?): String? {
        val fileType : String? = uri?.let { context.contentResolver.getType(it) }
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }


}