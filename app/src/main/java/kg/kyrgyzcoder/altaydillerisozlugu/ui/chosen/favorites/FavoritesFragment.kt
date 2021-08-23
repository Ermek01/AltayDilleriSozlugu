package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelDescFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelDescFavoritesItem
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentFavoritesBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.FavoriteListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.FavoritesPagerAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.GetDescFavoriteListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsPagerAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.util.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception

class FavoritesFragment : Fragment(), KodeinAware, GetDescFavoriteListener, FavoriteListener, FavoritesPagerAdapter.FavoriteClickListener {

    override val kodein: Kodein by closestKodein()
    private val chosenViewModelFactory: ChosenViewModelFactory by instance()

    private lateinit var chosenViewModel: ChosenViewModel

    private val favorites = arrayListOf<ModelDescFavoritesItem>()

    private lateinit var adapter: FavoritesPagerAdapter

    private var code: String? = ""
    private var position: Int? = null

    private val args: FavoritesFragmentArgs by navArgs()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = args.position

        chosenViewModel = ViewModelProvider(
            requireActivity(),
            chosenViewModelFactory
        ).get(ChosenViewModel::class.java)

        chosenViewModel.getDescFavoriteListener(this)
        chosenViewModel.addFavoriteListener(this)

        val pref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        code = pref.getString(CODE_KEY, "")

        binding.progressBar.show()
        chosenViewModel.getDescFavorites(code, position!!)

        binding.imgBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack(R.id.favoritesFragment, true)
            //Navigation.findNavController(binding.root).navigate(R.id.action_wordsFragment_to_mainFragment)
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


            val DRAWABLE_RIGHT = 2

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

    }



    override fun addFavoriteFailure(code: Int?) {

    }

    override fun addFavoriteSuccess() {

    }

    override fun getDescFavoritesSuccess(modelDescFavorites: ModelDescFavorites) {
        favorites.clear()
        favorites.addAll(modelDescFavorites)
        adapter = FavoritesPagerAdapter(requireContext(), favorites, this)
        binding.viewPager.adapter = adapter
        binding.progressBar.hide()
        adapter.notifyDataSetChanged()
        if (position != null) {
            binding.viewPager.currentItem = position!!
            binding.indicator.setViewPager(binding.viewPager)
        }

        binding.viewPager.getChildAt(0)

        if (code!!.isNotEmpty()) {

            try {
                when (code) {
                    "tr" -> {
                        binding.nameCards.text = modelDescFavorites[0].category
                    }
                    "ky" -> {
                        binding.nameCards.text = modelDescFavorites[0].category
                    }
                }
            }
            catch (e: Exception) {
                Log.d("ololo", e.toString())
            }


        }
        //binding.viewPager.offscreenPageLimit = descriptions.size


        binding.viewPager.pageMargin = 50

    }

    override fun getDescFavoritesError(code: Int?) {
        binding.progressBar.hide()
        requireActivity().toast("$code")
    }

    override fun onAddFavoriteClick(position: Int) {
        chosenViewModel.addFavorite(code, ModelFavorites(favorites[position].id))
    }

}