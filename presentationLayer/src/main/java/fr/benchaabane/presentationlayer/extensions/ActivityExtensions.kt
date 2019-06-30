package fr.benchaabane.presentationlayer.extensions

import android.app.Activity
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import fr.benchaabane.presentationlayer.R


val Activity.rootView: ViewGroup
    get() = findViewById(android.R.id.content)

fun Activity.findNavController() = findNavController(R.id.nav_host)

fun Activity.showSnackBar(message: String,
                          duration: Int = Snackbar.LENGTH_SHORT,
                          onDismiss: (() -> Unit)? = null) = rootView.showSnackbar(message, duration, onDismiss)

fun FragmentActivity.updateToolbar(title: String, isRoot: Boolean) {
    actionBar?.let {
        it.title = title
        it.setDisplayHomeAsUpEnabled(!isRoot)
    }
}


