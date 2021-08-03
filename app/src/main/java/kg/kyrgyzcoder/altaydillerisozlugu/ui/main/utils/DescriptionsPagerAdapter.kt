package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import android.annotation.SuppressLint
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
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptions
import kg.kyrgyzcoder.altaydillerisozlugu.util.*
import java.util.ArrayList

class DescriptionsPagerAdapter(
    private val context: Context,
    private val token: String?,
    private val modelDescriptions: ArrayList<ModelDescriptions>,
    private val listener: FavoriteClickListener
) : PagerAdapter() {

    private val words = arrayListOf<ModelDescriptions>()
    lateinit var adapter: DescriptionsRecyclerViewAdapter

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view =
            LayoutInflater.from(context).inflate(R.layout.row_descriptions_page, container, false)

        val model = modelDescriptions[position]

        val pref = context.getSharedPreferences("language", Context.MODE_PRIVATE)
        val code: String = pref.getString(CODE_KEY, "").toString()

        if (!model.image.isNullOrEmpty())
            Glide.with(view).load(model.image)
                .error(ContextCompat.getDrawable(context, R.drawable.def_image))
                .into(view.findViewById(R.id.img))

        getData(code, model, view)

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
                if (token.isNullOrEmpty()) {

                }
                else {
                    view.findViewById<ImageView>(R.id.favorite)
                        .setImageResource(R.drawable.ic_favorite_enable)
                }

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

    @SuppressLint("CutPasteId")
    private fun getData(code: String, model: ModelDescriptions, view: View) {
        if (code.isNotEmpty()) {
            when (code) {
                "tr" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_TR].title_tr
                }
                "ky" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_KY].title_ky
                }
                "az" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_AZ].title_az
                }
                "uz" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_UZ].title_uz
                }
                "kk" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_KK].title_kk
                }
                "ug" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_UG].title_ug
                }
                "tk" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_TK].title_tk
                }
                "tt" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_TT].title_tt
                }
                "ba" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_BA].title_ba
                }
                "cv" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_CV].title_cv
                }
                "kaa" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_KAA].title_kaa
                }
                "krc" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_KRC].title_krc
                }
                "sah" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_SAH].title_sah
                }
                "crh" -> {
                    view.findViewById<TextView>(R.id.tv_name)?.text = model.languages[TRANSLATE_CRH].title_crh
                }
            }
        }
    }

    private fun setupRecyclerViewAdapter(view: View, position: Int) {
        words.clear()
        words.addAll(modelDescriptions)
        adapter = DescriptionsRecyclerViewAdapter()
        view.findViewById<RecyclerView>(R.id.recyclerView_category_cards).adapter = adapter
        adapter.submitList(words[position].languages)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return modelDescriptions.size
    }

    interface FavoriteClickListener {
        fun onAddFavoriteClick(position: Int)
        fun onRemoveFavoriteClick(position: Int)
    }


}