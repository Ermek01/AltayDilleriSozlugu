package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesResItem
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.Word
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowWordsItemsChoosenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.favorites.ChosenFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.favorites.ChosenFragmentDirections
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.MainFragmentDirections
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FavoritesRecyclerViewAdapter(val activity: FragmentActivity) :
    ListAdapter<ModelFavoritesResItem, FavoritesRecyclerViewAdapter.ViewHolderCat>(DIFF),
    FavoritesWordsRecyclerAdapter.FavoritesClickListener,
    FavoritesWordsRecyclerAdapter.AddFavoritesClick{



    val userPreferences: UserPreferences? = null

    val words = arrayListOf<Word>()

    var defLang = "ky"

    var amount = 0

    lateinit var current: ModelFavoritesResItem

    val position: Int? = null

    lateinit var adapter: FavoritesWordsRecyclerAdapter

    fun getItemAtPos(position: Int): ModelFavoritesResItem {
        return getItem(position)
    }

    private var _binding: RowWordsItemsChoosenBinding? = null

    inner class ViewHolderCat(private val binding: RowWordsItemsChoosenBinding) :
        RecyclerView.ViewHolder(binding.root), KodeinAware {

        override val kodein: Kodein by binding.root.closestKodein()
        private val chosenViewModelFactory: ChosenViewModelFactory by instance()

        private lateinit var chosenViewModel: ChosenViewModel

        fun onBind(position: Int) {
            current = getItemAtPos(position)

            val pref = itemView.context.getSharedPreferences("language", Context.MODE_PRIVATE)
            val code : String = pref.getString(CODE_KEY, "").toString()

            if (code.isNotEmpty()){
                when(code){
                    "tr" -> {
                        binding.nameCategory.text = current.category_title
                    }
                    "ky" -> {

                        binding.nameCategory.text = current.category_title
                    }
                }
            }

            setupRecyclerView(binding, position, current)
            amount = current.category_id
        }
    }

    fun setupRecyclerView(
        binding: RowWordsItemsChoosenBinding,
        position: Int,
        current: ModelFavoritesResItem
    ) {
      /*  words.clear()
        words.addAll(current.words)*/
        adapter = FavoritesWordsRecyclerAdapter(activity,this, this)
        binding.recyclerView.adapter = adapter
        adapter.parent = position
        adapter.submitList(current.words)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = RowWordsItemsChoosenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat, position: Int) {
        holder.onBind(position)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelFavoritesResItem>() {
            override fun areItemsTheSame(oldItem: ModelFavoritesResItem, newItem: ModelFavoritesResItem): Boolean {
                return oldItem.category_id == newItem.category_id
            }

            override fun areContentsTheSame(
                oldItem: ModelFavoritesResItem,
                newItem: ModelFavoritesResItem
            ): Boolean {
                return oldItem.category_id == newItem.category_id
            }
        }
    }

    override fun onFavoritesClick(positionWords: Int, parent: Int) {
        val action = ChosenFragmentDirections.actionNavigationChoosenToFavoritesFragment(getItem(parent).category_id)
        Navigation.findNavController(_binding?.root!!).navigate(action)
    }

    override fun onAddFavoritesClick(position: Int, wordId: Int) {

    }

}