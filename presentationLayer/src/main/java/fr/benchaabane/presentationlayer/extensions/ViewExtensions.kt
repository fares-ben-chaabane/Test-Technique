package fr.benchaabane.presentationlayer.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import fr.benchaabane.presentationlayer.R

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


fun View.removeOnClickListener() {
    setOnClickListener(null)
}

fun View.findErrorView(): View = findViewById(R.id.error_view)
fun View.findLoadingView(): View = findViewById(R.id.loading_view)


// ViewGroup
fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this, false)


