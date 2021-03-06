package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelProfileUser
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentLogoutBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.SplashScreenActivity
import kg.kyrgyzcoder.altaydillerisozlugu.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class LogoutFragment : DialogFragment(), KodeinAware, LogoutListener {

    override val kodein: Kodein by closestKodein()
    private val profileViewModelFactory: ProfileViewModelFactory by instance()

    private lateinit var profileViewModel: ProfileViewModel

    private var _binding: FragmentLogoutBinding? = null
    private val binding: FragmentLogoutBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProvider(
            requireActivity(), profileViewModelFactory
        ).get(ProfileViewModel::class.java)
        profileViewModel.setLogoutListener(this)


        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnLogout.setOnClickListener {
            profileViewModel.logoutUser()
        }

    }

    override fun logoutSuccess() {
        val intent = Intent(requireContext(), SplashScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        requireActivity().finish()
        Toast.makeText(requireContext(), getString(R.string.txt_success_logout), Toast.LENGTH_SHORT).show()
    }

    override fun logoutFail(code: Int?) {
        Toast.makeText(requireContext(), "dasdsadsa", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams?
    }


}