package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

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
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentRegisterDialogBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.SplashScreenActivity
import kg.kyrgyzcoder.altaydillerisozlugu.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class RegisterDialogFragment : DialogFragment(){


    private var _binding: FragmentRegisterDialogBinding? = null
    private val binding: FragmentRegisterDialogBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDialogBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.register.setOnClickListener {
            val intent = Intent(requireContext(), SplashScreenActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            requireActivity().finish()
            dismiss()
        }

    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams?
    }


}