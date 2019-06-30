package fr.benchaabane.presentationlayer.ui.books.details

import android.os.Bundle
import android.view.View
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.di.injector
import fr.benchaabane.presentationlayer.extensions.updateToolbar
import fr.benchaabane.presentationlayer.ui.BaseFragment
import javax.inject.Inject

class BookDetailFragment: BaseFragment(R.layout.fragment_book_detail) {

    @Inject lateinit var viewModel: BookDetailsViewModel
    private var bookDetailsView: BookDetailsView? = null

    /* ------------------------------------- */
    /*               Lifecycle               */
    /* ------------------------------------- */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookDetailsView = BookDetailsView(view = view,
                                        lifecycleOwner = this,
                                        viewModel = viewModel,
                                        bookUic = arguments!!.getString(BOOK_UIC_NAVIGATION_ARG, ""))
    }

    override fun onResume() {
        super.onResume()
        activity?.updateToolbar(getString(R.string.book_details), false)
    }

    override fun onDestroy() {
        bookDetailsView?.destroy()
        bookDetailsView = null
        super.onDestroy()
    }
}

const val BOOK_UIC_NAVIGATION_ARG = "BOOK_UIC_NAVIGATION_ARG"