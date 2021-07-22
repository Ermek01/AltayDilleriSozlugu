package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesResItem
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.Word
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowWordsFavoritesBinding

import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel.ChosenViewModelFactory

import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

import org.kodein.di.generic.instance

class FavoritesWordsRecyclerAdapter(val activity: FragmentActivity,private val favoritesClickListener: FavoritesClickListener, private val addFavoritesClick: AddFavoritesClick):
    ListAdapter<Word, FavoritesWordsRecyclerAdapter.ViewHolderCat>(DIFF) {



    val list = arrayListOf<ModelFavoritesResItem>()

    var parent = -1

    val userPreferences: UserPreferences? = null

    var defLang = "ky"


    fun getItemAtPos(position: Int): Word {
        return getItem(position)
    }

    private var _binding: RowWordsFavoritesBinding? = null

    inner class ViewHolderCat(private val binding: RowWordsFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root), KodeinAware, FavoriteListener {

        override val kodein: Kodein by closestKodein(activity)
        private val chosenViewModelFactory: ChosenViewModelFactory by instance()

        private lateinit var chosenViewModel: ChosenViewModel

        fun onBind(position: Int) {
            val current = getItemAtPos(position)

            chosenViewModel = ViewModelProvider(
                activity,
                chosenViewModelFactory
            ).get(ChosenViewModel::class.java)

            chosenViewModel.addFavoriteListener(this)

            val pref = itemView.context.getSharedPreferences("language", Context.MODE_PRIVATE)
            val code : String = pref.getString(CODE_KEY, "").toString()

            if (code.isNotEmpty()){
                when(code){
                    "tr" -> {
                        Glide.with(binding.root).load(current.image)
                            .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                            .into(binding.img)

                        binding.nameWords.text = current.title

                    }
                    "ky" -> {
                        Glide.with(binding.root).load(current.image)
                            .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                            .into(binding.img)

                        binding.nameWords.text = current.title
                    }
                }
            }

            if (current.favorite) {
                binding.favorites.setImageResource(R.drawable.ic_favorite_enable)
            }
            else {
                binding.favorites.setImageResource(R.drawable.ic_favorite_disable)
            }

            binding.favorites.setOnClickListener {
                if (binding.favorites.drawable.constantState == ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_favorite_disable
                    )?.constantState
                ) {
                    chosenViewModel.addFavorite(code, ModelFavorites(current.id))
                    binding.favorites.setImageResource(R.drawable.ic_favorite_enable)
                } else {
                    chosenViewModel.addFavorite(code, ModelFavorites(current.id))
                    binding.favorites.setImageResource(R.drawable.ic_favorite_disable)
                }

            }

            binding.root.setOnClickListener {
                favoritesClickListener.onFavoritesClick(position, parent)
            }
        }

        override fun addFavoriteFailure(code: Int?) {

        }

        override fun addFavoriteSuccess() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = RowWordsFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat, position: Int) {
        holder.onBind(position)

    }

    interface FavoritesClickListener {
        fun onFavoritesClick(positionWords: Int, parent: Int)
    }

    interface AddFavoritesClick {
        fun onAddFavoritesClick(position: Int, wordId: Int)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Word,
                newItem: Word
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}