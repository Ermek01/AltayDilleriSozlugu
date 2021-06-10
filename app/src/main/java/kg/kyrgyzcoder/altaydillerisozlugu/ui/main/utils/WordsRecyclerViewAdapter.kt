package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategory
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelWords
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowCategoryItemsBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowWordsItemsBinding

class WordsRecyclerViewAdapter(
    private val listener: WordsClickListener
) :
    ListAdapter<ModelWords, WordsRecyclerViewAdapter.ViewHolderCat>(DIFF) {

    fun getItemAtPos(position: Int): ModelWords {
        return getItem(position)
    }

    private var _binding: RowWordsItemsBinding? = null

    inner class ViewHolderCat(private val binding: RowWordsItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItemAtPos(position)

//            if (!current.image.isNullOrEmpty())
//                Glide.with(binding.root).load(current.image)
//                    .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
//                    .into(binding.imgItems)

            binding.tvWordsName.text = current.title_ky

            binding.root.setOnClickListener {
                listener.onWordsClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = RowWordsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat, position: Int) {
        holder.onBind(position)
    }

    interface WordsClickListener {
        fun onWordsClick(position: Int)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelWords>() {
            override fun areItemsTheSame(oldItem: ModelWords, newItem: ModelWords): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ModelWords,
                newItem: ModelWords
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}