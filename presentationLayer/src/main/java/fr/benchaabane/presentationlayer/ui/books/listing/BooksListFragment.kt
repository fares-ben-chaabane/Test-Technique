package fr.benchaabane.presentationlayer.ui.books.listing

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import fr.benchaabane.presentationlayer.BuildConfig
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.di.injector
import fr.benchaabane.presentationlayer.extensions.cast
import fr.benchaabane.presentationlayer.extensions.updateToolbar
import fr.benchaabane.presentationlayer.ui.BaseFragment
import javax.inject.Inject

class BooksListFragment : BaseFragment(R.layout.fragment_books_list) {

    @Inject lateinit var viewModel: BookListViewModel
    private var bookListView: BookListView? = null
    private var delegate: Delegate? = null


    /* ------------------------------------- */
    /*               Lifecycle               */
    /* ------------------------------------- */

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        delegate = context?.cast()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        injector?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookListView = BookListView(view = view,
                                    lifecycleOwner = this,
                                    viewModel = viewModel,
                                    onBookChosen = { delegate?.goToBookDetails(it) })
    }


    override fun onResume() {
        super.onResume()
        activity?.updateToolbar(getString(R.string.books_list), true)
    }

    override fun onDestroy() {
        bookListView?.destroy()
        bookListView = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.book_list_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        if (BuildConfig.FAVORITE_ENABLED) {
            menu?.findItem(R.id.action_show_favorites)?.isVisible = true
            menu?.findItem(R.id.action_show_all)?.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_sort_asc -> {
                bookListView?.sortBooksList(isAsc = true)
                true
            }
            R.id.action_sort_desc -> {
                bookListView?.sortBooksList(isAsc = false)
                true
            }
            R.id.action_show_favorites -> {
                bookListView?.filterList(showFavorites = true)
                true
            }
            R.id.action_show_all -> {
                bookListView?.filterList(showFavorites = false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    /* ------------------------------------- */
    /*               Delegate                */
    /* ------------------------------------- */

    interface Delegate {
        fun goToBookDetails(uic: String)
    }


}