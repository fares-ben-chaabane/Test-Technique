package fr.benchaabane.presentationlayer.ui.books

import android.content.Context
import android.content.Intent
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.extensions.bundle
import fr.benchaabane.presentationlayer.tools.goTo
import fr.benchaabane.presentationlayer.ui.BaseActivity
import fr.benchaabane.presentationlayer.ui.books.details.BOOK_UIC_NAVIGATION_ARG
import fr.benchaabane.presentationlayer.ui.books.listing.BooksListFragment

class BooksActivity : BaseActivity(R.layout.activity_books), BooksListFragment.Delegate {

    /* ------------------------------------- */
    /*               Delegates               */
    /* ------------------------------------- */

    override fun goToBookDetails(uic: String) {
        goTo(TO_BOOK_DETAIL_NAVIGATION_ACTION, bundle { putString(BOOK_UIC_NAVIGATION_ARG, uic) })
    }

    /* ------------------------------------- */
    /*                Statics                */
    /* ------------------------------------- */

    companion object {
        fun newIntent(caller: Context) = Intent(caller, BooksActivity::class.java)
    }
}

private val TO_BOOK_DETAIL_NAVIGATION_ACTION = R.id.action_booksListFragment_to_bookDetailFragment