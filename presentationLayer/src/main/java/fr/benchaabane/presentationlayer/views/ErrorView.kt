package fr.benchaabane.presentationlayer.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.extensions.findErrorView
import fr.benchaabane.presentationlayer.extensions.makeGone
import fr.benchaabane.presentationlayer.extensions.show
import fr.benchaabane.presentationlayer.extensions.unsafeLazy


class ErrorView(private val view: View,
                title: String,
                icon: Int,
                onRefreshRequested: () -> Unit) {

    private val messageView by unsafeLazy { view.findViewById<TextView>(R.id.error_message) }
    private val refreshView by unsafeLazy { view.findViewById<View>(R.id.error_refresh) }

    init {
        view.findViewById<TextView>(R.id.error_title).text = title
        view.findViewById<ImageView>(R.id.error_image).setImageResource(icon)
        refreshView.setOnClickListener {
            onRefreshRequested()
        }
    }

    fun setMessage(message: String) {
        messageView.text = message
    }

    fun setMessage(messageResId: Int) {
        messageView.text = view.context.getString(messageResId)
    }

    fun hide() {
        view.makeGone()
    }

    fun show() {
        view.show()
    }

    fun setRefreshViewBackground(@DrawableRes drawableRes: Int) {
        refreshView.setBackgroundResource(drawableRes)
    }
}

fun errorView(view: View,
              title: String,
              icon: Int,
              onRefreshRequested: () -> Unit) = ErrorView(view.findErrorView(), title, icon, onRefreshRequested)

fun errorView(view: View,
              title: Int,
              icon: Int,
              onRefreshRequested: () -> Unit) = with(view) { ErrorView(findErrorView(), context.getString(title), icon, onRefreshRequested) }