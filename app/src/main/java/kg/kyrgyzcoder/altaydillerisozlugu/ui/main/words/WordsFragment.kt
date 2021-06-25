package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.words

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
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.*
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentMainBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentWordsBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.CategoryRecyclerViewAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.WordsListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.WordsRecyclerViewAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.util.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception
import java.util.ArrayList
import java.util.Locale.filter

class WordsFragment : Fragment(), KodeinAware, DescriptionsListener,
    WordsRecyclerViewAdapter.WordsClickListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private var _binding: FragmentWordsBinding? = null
    private val binding: FragmentWordsBinding get() = _binding!!

    private var search: String = ""

    private val words = mutableListOf<ModelDescriptions>()

    private lateinit var adapter: WordsRecyclerViewAdapter

    private var code: String? = ""

    val args: WordsFragmentArgs by navArgs()

    var amount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel = ViewModelProvider(
            requireActivity(),
            itemViewModelFactory
        ).get(ItemViewModel::class.java)

        itemViewModel.getDescriptionsListener(this)

        amount = args.id

        val pref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        code = pref.getString(CODE_KEY, "")

        binding.progressBar.show()
        itemViewModel.getDescriptionsList(code, amount, search)

        binding.swipeRefresh.setOnRefreshListener {
            binding.progressBar.show()
            itemViewModel.getDescriptionsList(code, amount, search)
        }

        binding.searchWords.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.progressBar.show()
                getDescriptionSearch()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.imgBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack(R.id.wordsFragment, true)
            //Navigation.findNavController(binding.root).navigate(R.id.action_wordsFragment_to_mainFragment)
        }

        binding.search.setOnClickListener {

            binding.search.visibility = View.GONE
            binding.mainIcon.visibility = View.GONE
            binding.searchWords.visibility = View.VISIBLE

            binding.searchWords.requestFocus()
            val imm =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        }

        binding.searchWords.setOnTouchListener(View.OnTouchListener { v, event ->

            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.searchWords.right - binding.searchWords.compoundDrawables.get(
                        DRAWABLE_RIGHT
                    ).bounds.width()
                ) {
                    binding.searchWords.visibility = View.GONE
                    binding.searchWords.setText("")
                    binding.search.visibility = View.VISIBLE
                    binding.mainIcon.visibility = View.VISIBLE
                    hideKeyboard(v)
                    return@OnTouchListener true
                }
            }
            false

        })

    }
    private fun getDescriptionSearch() {
        itemViewModel.getDescriptionsList(code, amount, binding.searchWords.text.toString())
        adapter = WordsRecyclerViewAdapter(this)
        binding.recyclerViewWords.setHasFixedSize(true)
        binding.recyclerViewWords.adapter = adapter
        binding.swipeRefresh.isRefreshing = false
        adapter.submitList(words)
        binding.progressBar.hide()
    }

    override fun onWordsClick(position: Int) {
        val action =
            WordsFragmentDirections.actionWordsFragmentToDescriptionFragment(amount, position)
        Navigation.findNavController(binding.root).navigate(action)

    }

    override fun getDescriptionsSuccess(modelDescriptionsPag: ModelDescriptionsPag) {
        words.clear()
        words.addAll(modelDescriptionsPag)
        adapter = WordsRecyclerViewAdapter(this)
        binding.recyclerViewWords.adapter = adapter
        adapter.submitList(words)
        binding.progressBar.hide()
        binding.swipeRefresh.isRefreshing = false

        if (code!!.isNotEmpty()) {

            try {
                when (code) {
                    "tr" -> {
                        binding.nameCards.text = modelDescriptionsPag[0].category
                    }
                    "ky" -> {
                        binding.nameCards.text = modelDescriptionsPag[0].category
                    }
                }
            }
            catch (e: Exception) {
                Log.d("ololo", e.toString())
            }
        }
    }

    override fun getDescriptionsError(code: Int?) {
        binding.progressBar.hide()
        Toast.makeText(activity, getString(R.string.txt_error_request), Toast.LENGTH_SHORT).show()
        binding.swipeRefresh.isRefreshing = false
    }

}