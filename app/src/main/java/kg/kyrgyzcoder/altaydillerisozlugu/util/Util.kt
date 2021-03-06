package kg.kyrgyzcoder.altaydillerisozlugu.util

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Typeface
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

val FLAG_KEY = "FLAG_KEY"
val CODE_KEY = "CODE_KEY"
val NAME_KEY = "NAME_KEY"
val LANGUAGE_KEY = "NAME_KEY"

const val TRANSLATE_TR = 0
const val TRANSLATE_AZ = 1
const val TRANSLATE_UZ = 2
const val TRANSLATE_KK = 3
const val TRANSLATE_UG = 4
const val TRANSLATE_TK = 5
const val TRANSLATE_TT = 6
const val TRANSLATE_KY = 7
const val TRANSLATE_KSK = 8
const val TRANSLATE_BA = 9
const val TRANSLATE_CV = 10
const val TRANSLATE_ASH = 11
const val TRANSLATE_KAA = 12
const val TRANSLATE_KRC = 13
const val TRANSLATE_SAH = 14
const val TRANSLATE_CRH = 15
const val TRANSLATE_ALT = 16

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}


fun Toolbar.changeToolbarFont(){
    for (i in 0 until childCount){
        val view = getChildAt(i)
        if (view is TextView && view.text == title){
            view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/customFont")
            break
        }
    }
}

fun Fragment.hideKeyboard(view: View) {
    view.let { activity?.hideKeyboard(it) }
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun RelativeLayout.show() {
    visibility = View.VISIBLE
}

fun RelativeLayout.hide() {
    visibility = View.GONE
}