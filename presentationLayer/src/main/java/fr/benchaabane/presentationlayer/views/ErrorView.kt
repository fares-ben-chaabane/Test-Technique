package fr.benchaabane.presentationlayer.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

    fun hide() {
        view.makeGone()
    }

    fun show() {
        view.show()
    }

}

fun errorView(view: View,
              title: Int,
              icon: Int,
              onRefreshRequested: () -> Unit) = with(view) { ErrorView(findErrorView(), context.getString(title), icon, onRefreshRequested) }