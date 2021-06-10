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
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowCategoryItemsBinding

class CategoryRecyclerViewAdapter(
    private val listener: CategoryClickListener
) :
    ListAdapter<ModelCategory, CategoryRecyclerViewAdapter.ViewHolderCat>(DIFF) {

    fun getItemAtPos(position: Int): ModelCategory {
        return getItem(position)
    }

    private var _binding: RowCategoryItemsBinding? = null

    inner class ViewHolderCat(private val binding: RowCategoryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItemAtPos(position)

            if (!current.image.isNullOrEmpty())
                Glide.with(binding.root).load(current.image)
                    .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                    .into(binding.imgItems)

            binding.nameCategory.text = current.title_ky

            binding.root.setOnClickListener {
                listener.onCategoryClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCat {
        _binding = RowCategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCat(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderCat, position: Int) {
        holder.onBind(position)
    }

    interface CategoryClickListener {
        fun onCategoryClick(position: Int)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelCategory>() {
            override fun areItemsTheSame(oldItem: ModelCategory, newItem: ModelCategory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ModelCategory,
                newItem: ModelCategory
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}