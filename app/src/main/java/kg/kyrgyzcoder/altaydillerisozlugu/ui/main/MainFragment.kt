package kg.kyrgyzcoder.altaydillerisozlugu.ui.main

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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategoryRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentMainBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.CategoryListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.CategoryRecyclerViewAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY
import kg.kyrgyzcoder.altaydillerisozlugu.util.hide
import kg.kyrgyzcoder.altaydillerisozlugu.util.hideKeyboard
import kg.kyrgyzcoder.altaydillerisozlugu.util.show
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception

class MainFragment : Fragment(), KodeinAware, CategoryListener, CategoryRecyclerViewAdapter.CategoryClickListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private val categories = mutableListOf<ModelCategory>()

    private lateinit var adapter: CategoryRecyclerViewAdapter
    private var search: String = ""
    private var code: String? = ""

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    var is_premium: Boolean? = null
    var token: String? = null

    private var currentLanguages = listOf<List<String?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel = ViewModelProvider(
            requireActivity(),
            itemViewModelFactory
        ).get(ItemViewModel::class.java)

        itemViewModel.getCategoryListener(this)

        itemViewModel.is_premium.asLiveData().observe(viewLifecycleOwner, {
            is_premium = it
        })

        itemViewModel.token.asLiveData().observe(viewLifecycleOwner, {
            token = it
        })

        val pref = requireActivity().getSharedPreferences("language",Context.MODE_PRIVATE)
        code = pref.getString(CODE_KEY, "")

        binding.progressBar.show()
        try {
            itemViewModel.getCategoryList(code, search)
        }
        catch (e : Exception) {
            Log.d("ololo", e.toString())
        }


        binding.swipeRefresh.setOnRefreshListener {
            binding.progressBar.show()
            itemViewModel.getCategoryList(code, search)
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
                getCategoriesSearch()
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
                    binding.editSearch.setText("")
                    binding.toolbar.visibility = View.VISIBLE
                    binding.search.visibility = View.VISIBLE
                    hideKeyboard(v)
                    return@OnTouchListener true
                }
            }
            false

        })


    }

    private fun getCategoriesSearch() {
        itemViewModel.getCategoryList(code, binding.editSearch.text.toString())
//        adapter = CategoryRecyclerViewAdapter(this)
//        binding.recyclerViewCategoryCards.setHasFixedSize(true)
//        binding.recyclerViewCategoryCards.adapter = adapter
//        binding.swipeRefresh.isRefreshing = false
//        adapter.submitList(categories)
    }

    override fun getCategories(modelCategoryRes: ModelCategoryRes) {
        categories.clear()
        categories.addAll(modelCategoryRes)
        adapter = CategoryRecyclerViewAdapter(this, is_premium, token)
        binding.recyclerViewCategoryCards.adapter = adapter
        adapter.submitList(categories)
        binding.progressBar.hide()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getCategoryError(code: Int?) {
        binding.progressBar.hide()
        Toast.makeText(activity, getString(R.string.txt_error_request), Toast.LENGTH_SHORT).show()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onCategoryClick(position: Int) {
        val amount = categories[position].id
        val action = MainFragmentDirections.actionMainFragmentToWordsFragment(amount)
        Navigation.findNavController(binding.root).navigate(action)
        binding.editSearch.setText("")
        requireActivity().hideKeyboard()
    }

    override fun onChangeLanguageText(position: Int) {
    }
}