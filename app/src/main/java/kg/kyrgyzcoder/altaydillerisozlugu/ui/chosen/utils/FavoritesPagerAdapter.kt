package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelDescFavoritesItem
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesResItem
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptions
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsRecyclerViewAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY
import kg.kyrgyzcoder.altaydillerisozlugu.util.TRANSLATE_KY
import kg.kyrgyzcoder.altaydillerisozlugu.util.TRANSLATE_TR

class FavoritesPagerAdapter(
    private val context : Context,
    private val favorites: ArrayList<ModelDescFavoritesItem>,
    private val listener: FavoriteClickListener
) : PagerAdapter() {

    private val words = arrayListOf<ModelDescFavoritesItem>()
    lateinit var adapter: FavoritesDescRecyclerViewAdapter

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(context).inflate(R.layout.row_descriptions_page, container, false)

        val model = favorites[position]

        val pref = context.getSharedPreferences("language", Context.MODE_PRIVATE)
        val code: String = pref.getString(CODE_KEY, "").toString()

        if (code.isNotEmpty()) {
            when (code) {
                "tr" -> {
                    getTranslateTr(model, view)
                }
                "ky" -> {
                    getTranslateKy(model, view)
                }
            }
        }

        if (model.favorite) {
            view.findViewById<ImageView>(R.id.favorite)
                .setImageResource(R.drawable.ic_favorite_enable)
        }
        else {
            view.findViewById<ImageView>(R.id.favorite)
                .setImageResource(R.drawable.ic_favorite_disable)
        }

        view.findViewById<ImageView>(R.id.favorite).setOnClickListener {
            if (view.findViewById<ImageView>(R.id.favorite).drawable.constantState == ContextCompat.getDrawable(
                    view.context,
                    R.drawable.ic_favorite_disable
                )?.constantState
            ) {
                listener.onAddFavoriteClick(position)
                view.findViewById<ImageView>(R.id.favorite)
                    .setImageResource(R.drawable.ic_favorite_enable)
            } else {
                listener.onAddFavoriteClick(position)
                view.findViewById<ImageView>(R.id.favorite)
                    .setImageResource(R.drawable.ic_favorite_disable)
            }

        }

        setupRecyclerViewAdapter(view, position)
        container.addView(view)

        return view
    }

    private fun setupRecyclerViewAdapter(view: View, position: Int) {
        words.clear()
        words.addAll(favorites)
        adapter = FavoritesDescRecyclerViewAdapter()
        view.findViewById<RecyclerView>(R.id.recyclerView_category_cards).adapter = adapter
        adapter.submitList(words[position].languages)
    }

    override fun getCount(): Int {
        return favorites.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun getTranslateKy(model: ModelDescFavoritesItem, view: View?) {
        if (!model.image.isNullOrEmpty())
            Glide.with(view!!).load(model.image)
                .error(ContextCompat.getDrawable(context, R.drawable.def_image))
                .into(view.findViewById(R.id.img))

        view?.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_KY].title_ky
    }

    private fun getTranslateTr(model: ModelDescFavoritesItem, view: View?) {
        if (!model.image.isNullOrEmpty())
            Glide.with(view!!).load(model.image)
                .error(ContextCompat.getDrawable(context, R.drawable.def_image))
                .into(view.findViewById(R.id.img))

        view?.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_TR].title_tr
    }

    interface FavoriteClickListener {
        fun onAddFavoriteClick(position: Int)
    }

}