package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.description

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptions
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptionsPag
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentDescriptionBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentMainBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsPagerAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.words.WordsFragmentArgs
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY
import kg.kyrgyzcoder.altaydillerisozlugu.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception
import kotlin.math.abs

class DescriptionFragment : Fragment(), KodeinAware, DescriptionsListener {

    override val kodein: Kodein by closestKodein()
    private val itemViewModelFactory: ItemViewModelFactory by instance()

    private lateinit var itemViewModel: ItemViewModel

    private val descriptions = arrayListOf<ModelDescriptions>()

    private lateinit var adapter: DescriptionsPagerAdapter

    private var code: String? = ""
    private var position: Int? = null

    private var _binding: FragmentDescriptionBinding? = null
    private val binding: FragmentDescriptionBinding get() = _binding!!

    private val args : DescriptionFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ololo", "onCreate")

    }

    override fun onStart() {
        super.onStart()

        Log.d("ololo", "onStart")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("ololo", "onCreateView")

        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ololo", "onViewCreated")

        val amount = args.id
        position = args.position

        itemViewModel = ViewModelProvider(
            requireActivity(),
            itemViewModelFactory
        ).get(ItemViewModel::class.java)

        itemViewModel.getDescriptionsListener(this)

        val pref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        code = pref.getString(CODE_KEY, "")

        itemViewModel.getDescriptionsList(code, amount)

        binding.imgBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack(R.id.descriptionFragment, true)
            //Navigation.findNavController(binding.root).navigate(R.id.action_wordsFragment_to_mainFragment)
        }

        binding.viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {

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

    override fun getDescriptionsSuccess(modelDescriptionsPag: ModelDescriptionsPag) {

        try {
            descriptions.clear()
            descriptions.addAll(modelDescriptionsPag)
            adapter = DescriptionsPagerAdapter(requireContext(), descriptions)
            binding.viewPager.adapter = adapter
            binding.indicator.setViewPager(binding.viewPager)
            adapter.notifyDataSetChanged()
            if (position != null){
                binding.viewPager.currentItem = position!!
            }

            binding.viewPager.getChildAt(0)

            if (code!!.isNotEmpty()){
                when(code){
                    "tr" -> {
                        binding.nameCards.text = modelDescriptionsPag[0].category
                    }
                    "ky" -> {
                        binding.nameCards.text = modelDescriptionsPag[0].category
                    }
                }
            }
            //binding.viewPager.offscreenPageLimit = descriptions.size


            binding.viewPager.pageMargin = 50
        }
        catch (e: Exception){
            Log.d("ololo", e.toString())
        }


    }

    override fun getDescriptionsError(code: Int?) {
        requireActivity().toast("$code")
    }
}