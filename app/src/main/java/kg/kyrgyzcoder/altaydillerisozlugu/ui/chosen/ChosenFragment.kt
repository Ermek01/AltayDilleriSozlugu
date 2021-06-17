package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentChosenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentWordsBinding
import kg.kyrgyzcoder.altaydillerisozlugu.util.hideKeyboard
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class ChosenFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private var _binding: FragmentChosenBinding? = null
    private val binding: FragmentChosenBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChosenBinding.inflate(inflater, container, false)
        return binding.root

        binding.search.setOnClickListener {

            binding.toolbar.visibility = View.GONE
            binding.search.visibility = View.GONE
            binding.editSearch.visibility = View.VISIBLE

            binding.editSearch.requestFocus()
            val imm =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        }

        binding.editSearch.setOnTouchListener(View.OnTouchListener { v, event ->

            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.editSearch.right - binding.editSearch.compoundDrawables.get(
                        DRAWABLE_RIGHT
                    ).bounds.width()
                ) {
                    binding.editSearch.visibility = View.GONE
                    binding.toolbar.visibility = View.VISIBLE
                    binding.search.visibility = View.VISIBLE
                    hideKeyboard(v)
                    return@OnTouchListener true
                }
            }
            false

        })



    }
}