package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptions
import kg.kyrgyzcoder.altaydillerisozlugu.util.CODE_KEY
import java.util.ArrayList

class DescriptionsPagerAdapter(private val context: Context, private val modelDescriptions: ArrayList<ModelDescriptions>) : PagerAdapter()  {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.row_descriptions_page, container, false)

        val model = modelDescriptions[position]

        val pref = context.getSharedPreferences("language", Context.MODE_PRIVATE)
        val code : String = pref.getString(CODE_KEY, "").toString()

        if (code.isNotEmpty()){
            when(code){
                "tr" -> {
                    if (!model.image.isNullOrEmpty())
                        Glide.with(view).load(model.image)
                            .error(ContextCompat.getDrawable(context, R.drawable.def_image))
                            .into(view.findViewById(R.id.img))

                    view.findViewById<TextView>(R.id.tv_name).text = model.word

                }
                "ky" -> {
                    if (!model.image.isNullOrEmpty())
                        Glide.with(view).load(model.image)
                            .error(ContextCompat.getDrawable(context, R.drawable.def_image))
                            .into(view.findViewById(R.id.img))

                    view.findViewById<TextView>(R.id.tv_name).text = model.word
                }
            }
        }

        container.addView(view, position)

        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return modelDescriptions.size
    }


}