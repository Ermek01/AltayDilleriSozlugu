package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.Language
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.RowDescriptionsItemsBinding
import kg.kyrgyzcoder.altaydillerisozlugu.util.*

class FavoritesDescRecyclerViewAdapter :
    ListAdapter<Language, FavoritesDescRecyclerViewAdapter.ViewHolder>(DIFF),
    MediaPlayer.OnPreparedListener {

    fun getItemAtPos(position: Int): Language {
        return getItem(position)
    }


    private var _binding: RowDescriptionsItemsBinding? = null

    inner class ViewHolder(private val binding: RowDescriptionsItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var mp = MediaPlayer()
        private var isPlaying = false

        fun onBind(holder: ViewHolder, position: Int) {

            val pref = itemView.context.getSharedPreferences("language", Context.MODE_PRIVATE)
            val code: String = pref.getString(CODE_KEY, "").toString()

            if (code.isNotEmpty()) {
                when (code) {
                    "tr" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "ky" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "az" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "uz" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "kk" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "ug" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "tk" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "tt" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "ba" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "cv" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "kaa" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "krc" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "sah" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "crh" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                            TRANSLATE_ALT -> {
                                getTranslatesAlt()
                            }
                        }
                    }
                    "alt" -> {
                        when (holder.itemViewType) {
                            TRANSLATE_TR -> {
                                getTranslatesTr()
                            }
                            TRANSLATE_AZ -> {
                                getTranslatesAz()
                            }
                            TRANSLATE_UZ -> {
                                getTranslatesUz()
                            }
                            TRANSLATE_KK -> {
                                getTranslatesKk()
                            }
                            TRANSLATE_UG -> {
                                getTranslatesUg()
                            }
                            TRANSLATE_TK -> {
                                getTranslatesTk()
                            }
                            TRANSLATE_TT -> {
                                getTranslatesTt()
                            }
                            TRANSLATE_KY -> {
                                getTranslatesKy()
                            }
                            TRANSLATE_KSK -> {
                                getTranslatesKsk()
                            }
                            TRANSLATE_BA -> {
                                getTranslatesBa()
                            }
                            TRANSLATE_CV -> {
                                getTranslatesCv()
                            }
                            TRANSLATE_ASH -> {
                                getTranslatesAsh()
                            }
                            TRANSLATE_KAA -> {
                                getTranslatesKaa()
                            }
                            TRANSLATE_KRC -> {
                                getTranslatesKrc()
                            }
                            TRANSLATE_SAH -> {
                                getTranslatesSah()
                            }
                            TRANSLATE_CRH -> {
                                getTranslatesCrh()
                            }
                            TRANSLATE_ALT -> {
                                binding.root.visibility = View.GONE
                                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
                            }
                        }
                    }
                }
            }
        }

        private fun getTranslatesTr() {
            Glide.with(binding.root).load(currentList[TRANSLATE_TR].image_tr)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.tr))
                .into(binding.img)
            binding.translateTxt.text = currentList[TRANSLATE_TR].title_tr
            binding.nameCountry.text = currentList[TRANSLATE_TR].language_tr

            if (currentList[TRANSLATE_TR].audio_file_tr != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayTr()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayTr() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_TR].audio_file_tr)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true
            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesAlt() {
            Glide.with(binding.root).load(currentList[TRANSLATE_ALT].image_alt)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_ALT].language_alt
            binding.translateTxt.text = currentList[TRANSLATE_ALT].title_alt

            if (currentList[TRANSLATE_ALT].audio_file_alt != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayAlt()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayAlt() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_ALT].audio_file_alt)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true
            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesCrh() {
            Glide.with(binding.root).load(currentList[TRANSLATE_CRH].image_crh)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.crh))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_CRH].language_crh
            binding.translateTxt.text = currentList[TRANSLATE_CRH].title_crh

            if (currentList[TRANSLATE_SAH].audio_file_sah != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayCrh()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }

        }

        private fun audioPlayCrh() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_CRH].audio_file_crh)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesSah() {
            Glide.with(binding.root).load(currentList[TRANSLATE_SAH].image_sah)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.sah))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_SAH].language_sah
            binding.translateTxt.text = currentList[TRANSLATE_SAH].title_sah

            if (currentList[TRANSLATE_SAH].audio_file_sah != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlaySah()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }

        }

        private fun audioPlaySah() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_SAH].audio_file_sah)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesKrc() {
            Glide.with(binding.root).load(currentList[TRANSLATE_KRC].image_krc)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.krc))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_KRC].language_krc
            binding.translateTxt.text = currentList[TRANSLATE_KRC].title_krc

            if (currentList[TRANSLATE_KRC].audio_file_krc != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayKrc()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayKrc() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_KRC].audio_file_krc)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesKaa() {
            Glide.with(binding.root).load(currentList[TRANSLATE_KAA].image_kaa)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_KAA].language_kaa
            binding.translateTxt.text = currentList[TRANSLATE_KAA].title_kaa

            if (currentList[TRANSLATE_KAA].audio_file_kaa != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayKaa()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }

        }

        private fun audioPlayKaa() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_KAA].audio_file_kaa)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesAsh() {
            Glide.with(binding.root).load(currentList[TRANSLATE_ASH].image_ash)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_ASH].language_ash
            binding.translateTxt.text = currentList[TRANSLATE_ASH].title_ash

            if (currentList[TRANSLATE_ASH].audio_file_ash != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayAsh()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }

        }

        private fun audioPlayAsh() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_ASH].audio_file_ash)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesCv() {
            Glide.with(binding.root).load(currentList[TRANSLATE_CV].image_cv)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_CV].language_cv
            binding.translateTxt.text = currentList[TRANSLATE_CV].title_cv

            if (currentList[TRANSLATE_CV].audio_file_cv != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayCv()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayCv() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_CV].audio_file_cv)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesBa() {
            Glide.with(binding.root).load(currentList[TRANSLATE_BA].image_ba)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.ba))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_BA].language_ba
            binding.translateTxt.text = currentList[TRANSLATE_BA].title_ba

            if (currentList[TRANSLATE_BA].audio_file_ba != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayBa()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }

        }

        private fun audioPlayBa() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_BA].audio_file_ba)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesKsk() {
            Glide.with(binding.root).load(currentList[TRANSLATE_KSK].image_ksk)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.gravity = Gravity.LEFT
            binding.nameCountry.text = currentList[TRANSLATE_KSK].language_ksk
            binding.translateTxt.text = currentList[TRANSLATE_KSK].title_ksk

            if (currentList[TRANSLATE_KSK].audio_file_ksk != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayKsk()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }

        }

        private fun audioPlayKsk() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_KSK].audio_file_ksk)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesKy() {

            Glide.with(binding.root).load(currentList[TRANSLATE_KY].image_ky)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.kg))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_KY].language_ky
            binding.translateTxt.text = currentList[TRANSLATE_KY].title_ky

            if (currentList[TRANSLATE_KY].audio_file_ky != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayKy()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }

        }

        private fun audioPlayKy() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_KY].audio_file_ky)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesTt() {
            Glide.with(binding.root).load(currentList[TRANSLATE_TT].image_tt)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_TT].language_tt
            binding.translateTxt.text = currentList[TRANSLATE_TT].title_tt

            if (currentList[TRANSLATE_TT].audio_file_tt != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayTt()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayTt() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_TT].audio_file_tt)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesTk() {
            Glide.with(binding.root).load(currentList[TRANSLATE_TK].image_tk)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_TK].language_tk
            binding.translateTxt.text = currentList[TRANSLATE_TK].title_tk

            if (currentList[TRANSLATE_TK].audio_file_tk != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayTk()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayTk() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_TK].audio_file_tk)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesUg() {
            Glide.with(binding.root).load(currentList[TRANSLATE_UG].image_ug)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.ug))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_UG].language_ug
            binding.translateTxt.text = currentList[TRANSLATE_UG].title_ug

            if (currentList[TRANSLATE_UG].audio_file_ug != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayUg()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayUg() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_UG].audio_file_ug)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesKk() {
            Glide.with(binding.root).load(currentList[TRANSLATE_KK].image_kk)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.def_image))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_KK].language_kk
            binding.translateTxt.text = currentList[TRANSLATE_KK].title_kk

            if (currentList[TRANSLATE_KK].audio_file_kk != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayKk()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayKk() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_KK].audio_file_kk)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesUz() {
            Glide.with(binding.root).load(currentList[TRANSLATE_UZ].image_uz)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.kg))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_UZ].language_uz
            binding.translateTxt.text = currentList[TRANSLATE_UZ].title_uz

            if (currentList[TRANSLATE_UZ].audio_file_uz != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayUz()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayUz() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_UZ].audio_file_uz)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

        private fun getTranslatesAz() {
            Glide.with(binding.root).load(currentList[TRANSLATE_AZ].image_az)
                .error(ContextCompat.getDrawable(binding.root.context, R.drawable.kg))
                .into(binding.img)
            binding.nameCountry.text = currentList[TRANSLATE_AZ].language_az
            binding.translateTxt.text = currentList[TRANSLATE_AZ].title_az

            if (currentList[TRANSLATE_AZ].audio_file_az != null) {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                binding.playBtn.setOnClickListener {
                    audioPlayAz()
                }
            } else {
                binding.playBtn.setImageResource(R.drawable.ic_group_disable)
            }
        }

        private fun audioPlayAz() {
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            } else {
                mp = MediaPlayer.create(
                    itemView.context,
                    Uri.parse(currentList[TRANSLATE_KY].audio_file_az)
                )
                mp.start()
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_enable)
                isPlaying = true


            }

            mp.setOnCompletionListener {
                binding.playBtn.setImageResource(R.drawable.ic_play_btn_disable)
                isPlaying = false
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        fun setupView(): ViewHolder {
            _binding = RowDescriptionsItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(_binding!!)
        }

        when (viewType) {
            TRANSLATE_TR -> setupView()
            TRANSLATE_AZ -> setupView()
            TRANSLATE_UZ -> setupView()
            TRANSLATE_KK -> setupView()
            TRANSLATE_UG -> setupView()
            TRANSLATE_TK -> setupView()
            TRANSLATE_TT -> setupView()
            TRANSLATE_KY -> setupView()
            TRANSLATE_KSK -> setupView()
            TRANSLATE_BA -> setupView()
            TRANSLATE_CV -> setupView()
            TRANSLATE_ASH -> setupView()
            TRANSLATE_KAA -> setupView()
            TRANSLATE_KRC -> setupView()
            TRANSLATE_SAH -> setupView()
            TRANSLATE_CRH -> setupView()
            else -> setupView()
        }

        _binding = RowDescriptionsItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(_binding!!)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(holder, position)
    }

    override fun getItemViewType(position: Int): Int {

        when (position) {
            0 -> return TRANSLATE_TR
            1 -> return TRANSLATE_AZ
            2 -> return TRANSLATE_UZ
            3 -> return TRANSLATE_KK
            4 -> return TRANSLATE_UG
            5 -> return TRANSLATE_TK
            6 -> return TRANSLATE_TT
            7 -> return TRANSLATE_KY
            8 -> return TRANSLATE_KSK
            9 -> return TRANSLATE_BA
            10 -> return TRANSLATE_CV
            11 -> return TRANSLATE_ASH
            12 -> return TRANSLATE_KAA
            13 -> return TRANSLATE_KRC
            14 -> return TRANSLATE_SAH
            15 -> return TRANSLATE_CRH
            else -> return TRANSLATE_ALT
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Language>() {
            override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Language,
                newItem: Language
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

}