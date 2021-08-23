package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.description

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager

import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.Languages
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptions
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptionsPag
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentDescriptionBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.FavoriteListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.RegisterDialogFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsPagerAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.util.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception

class DescriptionFragment : Fragment(), KodeinAware,
    DescriptionsPagerAdapter.FavoriteClickListener, FavoriteListener, DescriptionsListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()
    private val chosenViewModelFactory: ChosenViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var chosenViewModel: ChosenViewModel

    private var descriptions = mutableListOf<ModelDescriptions>()
    private var filteredList = mutableListOf<ModelDescriptions>()

    private lateinit var adapter: DescriptionsPagerAdapter

    private var code: String? = ""
    private var position: Int? = null
    private var userId = 0
    private var search: String = ""
    private var token: String? = null

    private var _binding: FragmentDescriptionBinding? = null
    private val binding: FragmentDescriptionBinding get() = _binding!!

    private val args: DescriptionFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemViewModel = ViewModelProvider(
            requireActivity(),
            itemViewModelFactory
        ).get(ItemViewModel::class.java)

        chosenViewModel = ViewModelProvider(
            requireActivity(),
            chosenViewModelFactory
        ).get(ChosenViewModel::class.java)

        chosenViewModel.addFavoriteListener(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val amount = args.id
        position = args.position
        itemViewModel.liveData.observe(viewLifecycleOwner, Observer {
            descriptions = it
        })
        chosenViewModel.token.asLiveData().observe(viewLifecycleOwner, {
            token = it
            getData()
        })

        val pref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        code = pref.getString(CODE_KEY, "")

        //itemViewModel.getDescriptionsList(code, amount, search)
        binding.imgBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack(R.id.descriptionFragment, true)
        }

        binding.search.setOnClickListener {

            binding.search.visibility = View.GONE
            binding.mainIcon.visibility = View.GONE
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
                    binding.editSearch.setText("")
                    binding.search.visibility = View.VISIBLE
                    binding.mainIcon.visibility = View.VISIBLE
                    hideKeyboard(v)
                    return@OnTouchListener true
                }
            }
            false

        })

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        binding.editSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.editSearch.text.isNotEmpty()) {
                    getDescriptionSearch(amount)
                }
                else {
                    getData()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun getData() {
        adapter = DescriptionsPagerAdapter(requireContext(), token, descriptions, this)
        binding.viewPager.adapter = adapter
        adapter.notifyDataSetChanged()
        if (position != null) {
            binding.viewPager.currentItem = position!!
            binding.indicator.setViewPager(binding.viewPager)
        }
        binding.viewPager.getChildAt(0)
        binding.nameCards.text = descriptions[0].category

        binding.viewPager.offscreenPageLimit = descriptions.size

        binding.viewPager.pageMargin = 50
        binding.progressBar.hide()
    }

    private fun getDescriptionSearch(amount: Int) {
        itemViewModel.getDescriptionsListener(this)
        itemViewModel.getDescriptionsList(code, amount, binding.editSearch.text.toString())
    }

    override fun onAddFavoriteClick(position: Int) {

        if (token.isNullOrEmpty()) {

            val fm = fragmentManager
            val registerFragment = RegisterDialogFragment()
            registerFragment.show(fm!!, "")


        } else {
            chosenViewModel.addFavorite(code, ModelFavorites(descriptions[position].id))
        }
    }

    override fun onRemoveFavoriteClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun addFavoriteFailure(code: Int?) {
        requireActivity().toast(getString(R.string.txt_error_add_favorite))
    }

    override fun addFavoriteSuccess() {

    }

    override fun getDescriptionsSuccess(modelDescriptionsPag: ModelDescriptionsPag) {

        filteredList.clear()
        filteredList.addAll(modelDescriptionsPag)
        try {
            binding.nameCards.text = filteredList[0].category
        }
        catch (e: Exception) {
            Log.e("ololo", e.toString())
        }

        adapter = DescriptionsPagerAdapter(requireContext(), token, filteredList, this)
        binding.viewPager.adapter = adapter
        adapter.notifyDataSetChanged()
        if (position != null) {
            binding.viewPager.currentItem = position!!
            binding.indicator.setViewPager(binding.viewPager)
        }
        binding.viewPager.getChildAt(0)


        binding.viewPager.offscreenPageLimit = filteredList.size

        binding.viewPager.pageMargin = 50
        binding.progressBar.hide()
    }

    override fun getDescriptionsError(code: Int?) {

    }

}
