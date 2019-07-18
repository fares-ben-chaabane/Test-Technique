package fr.benchaabane.presentationlayer.ui.books.listing

import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.drawee.view.SimpleDraweeView
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.extensions.*
import fr.benchaabane.presentationlayer.tools.ListAdapter
import fr.benchaabane.presentationlayer.tools.ViewHolder
import fr.benchaabane.presentationlayer.tools.loadUrl
import fr.benchaabane.presentationlayer.views.errorView


class BookListView(private val view: View,
                   lifecycleOwner: LifecycleOwner,
                   private val viewModel: BookListViewModel,
                   private val onBookChosen: (uic: String) -> Unit) {

    private val bookSwipeRefreshView by unsafeLazy { view.findViewById<SwipeRefreshLayout>(R.id.books_swipe_refresh)
        .apply { setOnRefreshListener {
            viewModel.refreshBookList()
        } } }
    private val booksRecycler by unsafeLazy { view.findViewById<RecyclerView>(R.id.books_recycler) }
    private val errorView by unsafeLazy { errorView(view, fr.benchaabane.presentationlayer.R.string.error_title, R.drawable.ic_error) { viewModel.retry() }  }
    private val loadingView by unsafeLazy { view.findLoadingView() }

    private val booksObserver = Observer<List<BookPreviewUi>> {
       it?.let { list -> populateBooksList(list) }
    }
    private val loadingObserver = Observer<Boolean> {
        if (it == true) loadingView.show() else loadingView.makeGone()
    }
    private val errorObserver = Observer<Boolean> {
        if (it == true) errorView.show() else errorView.hide()
    }
    private val refreshingObserver = Observer<Boolean> {
        bookSwipeRefreshView.isRefreshing = it == true
    }


    init {
        errorView.forceLoad()
        loadingView.forceLoad()
        bookSwipeRefreshView.forceLoad()
        booksRecycler.addItemDecoration(GridSpacingItemDecoration(spanCount = 2, spacing = 20, includeEdge = false))
        viewModel.observeBookList().observe(lifecycleOwner, booksObserver)
        viewModel.observeErrorState().observe(lifecycleOwner, errorObserver)
        viewModel.observeLoadingState().observe(lifecycleOwner, loadingObserver)
        viewModel.observerRefreshingState().observe(lifecycleOwner, refreshingObserver)
        viewModel.fetchBooks()
    }

    fun destroy() {
        viewModel.observeBookList().removeObserver(booksObserver)
        viewModel.observeErrorState().removeObserver(errorObserver)
        viewModel.observeLoadingState().removeObserver(loadingObserver)
        viewModel.observerRefreshingState().removeObserver(refreshingObserver)
        viewModel.destroySelf()
    }


    private fun populateBooksList(books: List<BookPreviewUi>) {
        booksRecycler.adapter = ListAdapter(items = books,
                                            layout = R.layout.item_book_holder,
                                            createViewHolder = { itemView ->
                                                BookPreviewViewHolder(view = itemView,
                                                    onBookChosen = { onBookChosen.invoke(it) })})

    }


    inner class BookPreviewViewHolder(view: View,
                                      private val onBookChosen: (uic: String) -> Unit): ViewHolder<BookPreviewUi>(view) {

        private val coverView by unsafeLazy { view.findViewById<SimpleDraweeView>(R.id.book_cover_view) }
        private val bookTitleView by unsafeLazy { view.findViewById<TextView>(R.id.book_title_view) }
        private val bookAuthorView by unsafeLazy { view.findViewById<TextView>(R.id.book_author_view) }
        private val bookDistributionView by unsafeLazy { view.findViewById<TextView>(R.id.book_distribution_view) }

        override fun bind(item: BookPreviewUi) {
            super.bind(item)
            with(item) {
                coverView.loadUrl(coverUrl)
                bookTitleView.text = title
                bookAuthorView.text = author
                bookDistributionView.text = distribution
            }
            itemView.setOnClickListener {  onBookChosen.invoke(item.uic) }
        }

        override fun unbind() {
            itemView.removeOnClickListener()
            super.unbind()
        }
    }

    inner class GridSpacingItemDecoration(private val spanCount: Int,
                                          private val spacing: Int,
                                          private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }
}