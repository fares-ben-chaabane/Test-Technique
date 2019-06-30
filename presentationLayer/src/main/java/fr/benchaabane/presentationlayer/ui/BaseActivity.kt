package fr.benchaabane.presentationlayer.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.di.injector
import fr.benchaabane.presentationlayer.extensions.forceLoad
import fr.benchaabane.presentationlayer.extensions.unsafeLazy
import fr.benchaabane.presentationlayer.tools.Navigable
import fr.benchaabane.presentationlayer.tools.Router
import javax.inject.Inject

open class BaseActivity(@LayoutRes private val layoutResId: Int): FragmentActivity(), Navigable {

    @Inject override lateinit var router: Router
    private val appBar by unsafeLazy {
        findViewById<Toolbar>(R.id.app_toolbar)?.let {
            setActionBar(it)
        }
    }

    /* ------------------------------------- */
    /*               Lifecycle               */
    /* ------------------------------------- */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        injector?.inject(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        appBar.forceLoad()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        appBar.forceLoad()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        appBar.forceLoad()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}