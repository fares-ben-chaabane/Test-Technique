package fr.benchaabane.presentationlayer.ui.books.details

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.facebook.drawee.view.SimpleDraweeView
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.extensions.*
import fr.benchaabane.presentationlayer.tools.loadUrl
import fr.benchaabane.presentationlayer.views.errorView

class BookDetailsView(view: View,
                      lifecycleOwner: LifecycleOwner,
                      private val viewModel: BookDetailsViewModel,
                      bookUic: String) {

    private val errorView by unsafeLazy { errorView(view, R.string.error_title, R.drawable.ic_error) { viewModel.retry() }  }
    private val loadingView by unsafeLazy { view.findLoadingView() }

    private val coverView by unsafeLazy { view.findViewById<SimpleDraweeView>(R.id.book_cover_view) }
    private val bookTitleView by unsafeLazy { view.findViewById<TextView>(R.id.book_title_view) }
    private val bookAuthorView by unsafeLazy { view.findViewById<TextView>(R.id.book_author_view) }
    private val bookDistributionView by unsafeLazy { view.findViewById<TextView>(R.id.book_distribution_view) }
    private val bookSummaryView by unsafeLazy { view.findViewById<TextView>(R.id.book_summary_view) }
    private val bookPagesView by unsafeLazy { view.findViewById<TextView>(R.id.book_pages_count_view) }
    private val bookRatingView by unsafeLazy { view.findViewById<RatingBar>(R.id.book_rating_bar)
        .apply { isEnabled = false } }

    private val bookDetailObserver = Observer<BookDetailUi> {
        if (it != null) fillBookDetails(it)
    }

    private val errorStateObserver = Observer<Boolean> {
        if (it == true) errorView.show() else errorView.hide()
    }
    private val loadingStateObserver = Observer<Boolean> {
        if (it == true) loadingView.show() else loadingView.makeGone()
    }

    init {
        bookRatingView.forceLoad()
        viewModel.observeBookDetail().observe(lifecycleOwner, bookDetailObserver)
        viewModel.observeErrorState().observe(lifecycleOwner, errorStateObserver)
        viewModel.observerLoadingState().observe(lifecycleOwner, loadingStateObserver)
        viewModel.fetchBookDetails(bookUic)
    }

    fun destroy() {
        viewModel.observeBookDetail().removeObserver(bookDetailObserver)
        viewModel.observeErrorState().removeObserver(errorStateObserver)
        viewModel.observerLoadingState().removeObserver(loadingStateObserver)
        viewModel.destroySelf()
    }

    private fun fillBookDetails(details: BookDetailUi) {
        with(details) {
            coverView.loadUrl(coverUrl)
            bookTitleView.text = title
            bookAuthorView.text = author
            bookDistributionView.text = distribution
            bookSummaryView.text = summary
            bookPagesView.text = pagesCount
            bookRatingView.rating = rating.toFloat()
        }
    }
}