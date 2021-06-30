package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesResItem
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentChosenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.FavoriteListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.FavoritesRecyclerViewAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.FavoritesWordsRecyclerAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.GetFavoriteListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.MainFragmentDirections
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY
import kg.kyrgyzcoder.altaydillerisozlugu.util.hide
import kg.kyrgyzcoder.altaydillerisozlugu.util.hideKeyboard
import kg.kyrgyzcoder.altaydillerisozlugu.util.show
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.ArrayList

class ChosenFragment : Fragment(), KodeinAware, GetFavoriteListener, FavoriteListener {

    override val kodein: Kodein by closestKodein()
    private val chosenViewModelFactory: ChosenViewModelFactory by instance()

    private lateinit var chosenViewModel: ChosenViewModel

    private var _binding: FragmentChosenBinding? = null
    private val binding: FragmentChosenBinding get() = _binding!!

    private val favorites = mutableListOf<ModelFavoritesResItem>()

    private lateinit var adapter: FavoritesRecyclerViewAdapter

    private var code: String? = ""
    private var search: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChosenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chosenViewModel = ViewModelProvider(
            requireActivity(),
            chosenViewModelFactory
        ).get(ChosenViewModel::class.java)

        chosenViewModel.getFavoriteListener(this)
        chosenViewModel.addFavoriteListener(this)

        val pref = requireActivity().getSharedPreferences("language",Context.MODE_PRIVATE)
        code = pref.getString(CODE_KEY, "")

        binding.progressBar.show()
        chosenViewModel.getFavorites(code, search)

        binding.swipeRefresh.setOnRefreshListener {
            binding.progressBar.show()
            chosenViewModel.getFavorites(code, search)
        }

        binding.search.setOnClickListener {

            binding.toolbar.visibility = View.GONE
            binding.search.visibility = View.GONE
            binding.editSearch.visibility = View.VISIBLE

            binding.editSearch.requestFocus()
            val imm =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        }

        binding.editSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                getFavoritesSearch()
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })

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
                    binding.editSearch.setText("")
                    hideKeyboard(v)
                    return@OnTouchListener true
                }
            }
            false

        })

    }

    private fun getFavoritesSearch() {
        chosenViewModel.getFavorites(code, binding.editSearch.text.toString())
//        adapter = FavoritesRecyclerViewAdapter(this)
//        binding.recyclerViewWords.setHasFixedSize(true)
//        binding.recyclerViewWords.adapter = adapter
//        binding.swipeRefresh.isRefreshing = false
//        adapter.submitList(favorites)
    }

    override fun getFavoritesSuccess(modelFavoritesRes: ModelFavoritesRes) {
        favorites.clear()
        favorites.addAll(modelFavoritesRes)
        adapter = FavoritesRecyclerViewAdapter()
        binding.recyclerViewWords.adapter = adapter
        adapter.submitList(favorites)
        binding.progressBar.hide()
        binding.swipeRefresh.isRefreshing = false

    }

    override fun getFavoritesError(code: Int?) {
        binding.progressBar.hide()
        Toast.makeText(activity, getString(R.string.txt_error_request), Toast.LENGTH_SHORT).show()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun addFavoriteFailure(code: Int?) {

    }

    override fun addFavoriteSuccess() {
        binding.progressBar.hide()
    }

//    override fun onFavoritesClick(position: Int) {
//        val amount = favorites[position].category_id
//        val action = ChosenFragmentDirections.actionNavigationChoosenToFavoritesFragment(amount)
//        Navigation.findNavController(binding.root).navigate(action)
//    }
}