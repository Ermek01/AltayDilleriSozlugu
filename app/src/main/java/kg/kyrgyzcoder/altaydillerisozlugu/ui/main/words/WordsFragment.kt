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
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelWords
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelWordsPag
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentMainBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentWordsBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.CategoryRecyclerViewAdapter
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

class WordsFragment : Fragment(), KodeinAware, WordsListener,
    WordsRecyclerViewAdapter.WordsClickListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private var _binding: FragmentWordsBinding? = null
    private val binding: FragmentWordsBinding get() = _binding!!

    private var search: String = ""

    private val words = mutableListOf<ModelWords>()

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

        itemViewModel.getWordsListener(this)

        amount = args.id

        val pref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        code = pref.getString(CODE_KEY, "")

        binding.progressBar.show()
        itemViewModel.getWordsList(code, amount, search)

        binding.swipeRefresh.setOnRefreshListener {
            itemViewModel.getWordsList(code, amount, search)
        }

        binding.imgBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack(R.id.wordsFragment, true)
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
                    binding.search.visibility = View.VISIBLE
                    binding.mainIcon.visibility = View.VISIBLE
                    hideKeyboard(v)
                    return@OnTouchListener true
                }
            }
            false

        })

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                getWordsSearch()
            }


        })

    }

    private fun getWordsSearch() {
        itemViewModel.getWordsList(code, amount, binding.editSearch.text.toString())
        adapter = WordsRecyclerViewAdapter(this)
        binding.recyclerViewWords.setHasFixedSize(true)
        binding.recyclerViewWords.adapter = adapter
        binding.swipeRefresh.isRefreshing = false
        adapter.submitList(words)
    }

    override fun getWordsSuccess(modelWordsPag: ModelWordsPag) {
        words.clear()
        words.addAll(modelWordsPag)
        adapter = WordsRecyclerViewAdapter(this)
        binding.recyclerViewWords.adapter = adapter
        adapter.submitList(words)
        binding.progressBar.hide()
        binding.swipeRefresh.isRefreshing = false

        if (code!!.isNotEmpty()) {
            when (code) {
                "tr" -> {
                    binding.nameCards.text = modelWordsPag[0].category
                }
                "ky" -> {
                    binding.nameCards.text = modelWordsPag[0].category
                }
            }
        }

    }

    override fun getWordsError(code: Int?) {
        binding.progressBar.hide()
        Toast.makeText(activity, getString(R.string.txt_error_request), Toast.LENGTH_SHORT).show()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onWordsClick(position: Int) {
        val action =
            WordsFragmentDirections.actionWordsFragmentToDescriptionFragment(amount, position)
        Navigation.findNavController(binding.root).navigate(action)

    }

}