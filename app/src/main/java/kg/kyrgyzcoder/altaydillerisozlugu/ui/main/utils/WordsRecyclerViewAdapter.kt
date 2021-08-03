package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.*
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowWordsItemsBinding
import kg.kyrgyzcoder.altaydillerisozlugu.util.*

class WordsRecyclerViewAdapter(
    private val listener: WordsClickListener
) :
    ListAdapter<ModelDescriptions, WordsRecyclerViewAdapter.ViewHolderCat>(DIFF) {

    fun getItemAtPos(position: Int): ModelDescriptions {
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
                        binding.tvWordsName.text = current.languages[TRANSLATE_TR].title_tr
                    }
                    "ky" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_KY].title_ky
                    }
                    "az" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_AZ].title_az
                    }
                    "uz" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_UZ].title_uz
                    }
                    "kk" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_KK].title_kk
                    }
                    "ug" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_UG].title_ug
                    }
                    "tk" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_TK].title_kk
                    }
                    "tt" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_TT].title_tt
                    }
                    "ba" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_BA].title_ba
                    }
                    "cv" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_CV].title_cv
                    }
                    "kaa" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_KAA].title_kaa
                    }
                    "krc" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_KRC].title_krc
                    }
                    "sah" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_SAH].title_sah
                    }
                    "crh" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_CRH].title_crh
                    }
                    "alt" -> {
                        binding.tvWordsName.text = current.languages[TRANSLATE_ALT].title_alt
                    }
                }
            }

            binding.root.setOnClickListener {
                listener.onWordsClick(position, currentList[position])
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
        fun onWordsClick(position: Int, current: ModelDescriptions)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelDescriptions>() {
            override fun areItemsTheSame(oldItem: ModelDescriptions, newItem: ModelDescriptions): Boolean {
                return oldItem.languages == newItem.languages
            }

            override fun areContentsTheSame(
                oldItem: ModelDescriptions,
                newItem: ModelDescriptions
            ): Boolean {
                return oldItem.languages == newItem.languages
            }
        }
    }

}