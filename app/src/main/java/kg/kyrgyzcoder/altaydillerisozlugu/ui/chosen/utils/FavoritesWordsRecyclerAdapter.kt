package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesResItem
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.Word
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowWordsFavoritesBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowWordsItemsChoosenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.favorites.ChosenFragmentDirections
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.MainFragmentDirections
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY

class FavoritesWordsRecyclerAdapter(private val favoritesClickListener: FavoritesClickListener, private val addFavoritesClick: AddFavoritesClick):
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
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItemAtPos(position)


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
                    addFavoritesClick.onAddFavoritesClick(position, current.id)
                    binding.favorites.setImageResource(R.drawable.ic_favorite_enable)
                } else {
                    addFavoritesClick.onAddFavoritesClick(position, current.id)
                    binding.favorites.setImageResource(R.drawable.ic_favorite_disable)
                }

            }

            binding.root.setOnClickListener {
                favoritesClickListener.onFavoritesClick(position, parent)
            }
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