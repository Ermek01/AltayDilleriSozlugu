package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import android.content.Context
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
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY

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

            val pref = itemView.context.getSharedPreferences("language", Context.MODE_PRIVATE)
            val code : String = pref.getString(CODE_KEY, "").toString()

            if (code.isNotEmpty()){
                when(code){
                    "tr" -> {
                        binding.tvWordsName.text = current.title_tr
                    }
                    "ky" -> {
                        binding.tvWordsName.text = current.title_ky
                    }
                }
            }

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