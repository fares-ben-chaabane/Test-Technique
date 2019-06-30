package fr.benchaabane.presentationlayer.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import fr.benchaabane.presentationlayer.R

// View
fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnackbar(message: String,
                      duration: Int = Snackbar.LENGTH_SHORT,
                      onDismiss: (() -> Unit)? = null,
                      action: Action? = null) {
    with(Snackbar.make(this, message, duration)) {
        action?.let { setAction(it.first) { _ -> it.second() } }
        if (onDismiss != null) addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                onDismiss()
            }
        })
        show()
    }
}

fun View.showSnackbar(@StringRes message: Int,
                      duration: Int = Snackbar.LENGTH_SHORT,
                      onDismiss: (() -> Unit)? = null) = showSnackbar(context.getString(message), duration, onDismiss)

typealias Action = Pair<String, () -> Unit>

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.animate(animationResId: Int, animationListener: Animation.AnimationListener? = null) {
    visibility = View.VISIBLE
    val animation = AnimationUtils.loadAnimation(context, animationResId)
    with(animation) {
        interpolator = AccelerateDecelerateInterpolator()
        fillBefore = true
        setAnimationListener(animationListener)
    }
    startAnimation(animation)
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.removeOnClickListener() {
    setOnClickListener(null)
}

fun View.findErrorView(): View = findViewById(R.id.error_view)
fun View.findLoadingView(): View = findViewById(R.id.loading_view)


// ViewGroup
fun ViewGroup.addView(layoutId: Int): View = inflate(layoutId).also { addView(it) }

fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this, false)



// TextView
fun TextView.clear() {
    text = ""
}

fun TextView.setTextOrHide(text: String?) {
    this.text = text
    if (text.isNullOrEmpty()) hide()
}

fun TextView.setTextOrMakeGone(text: String?) {
    this.text = text
    if (text.isNullOrEmpty()) makeGone()
    else show()
}

private fun TextView.setColor(color: Int) {
    setTextColor(color)
    compoundDrawablesRelative.first()?.colorFilter = PorterDuffColorFilter(color,
                                                                           PorterDuff.Mode.SRC_IN)
}

fun TextView.setColorResId(colorResId: Int) {
    setColor(ContextCompat.getColor(context, colorResId))
}


// Group
fun Group.setViewsClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

fun Group.removeViewsClickListener() {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(null)
    }
}
