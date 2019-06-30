package fr.benchaabane.presentationlayer.ui.books

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import fr.benchaabane.commons.os.some
import fr.benchaabane.domainlayer.books.RetrieveBookDetailsUseCase
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.extensions.bundle
import fr.benchaabane.presentationlayer.test.BaseTest
import fr.benchaabane.presentationlayer.test.DataBook
import fr.benchaabane.presentationlayer.test.TestFragmentRule
import fr.benchaabane.presentationlayer.tools.AndroidResources
import fr.benchaabane.presentationlayer.ui.books.details.BOOK_UIC_NAVIGATION_ARG
import fr.benchaabane.presentationlayer.ui.books.details.BookDetailFragment
import fr.benchaabane.presentationlayer.ui.books.details.BookDetailsViewModel
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookDetailsFragmentTest : BaseTest() {

    private lateinit var useCase: RetrieveBookDetailsUseCase
    private lateinit var data: DataBook

    @Rule
    @JvmField var fragmentRule = TestFragmentRule.create()

    @Before
    fun setUp() {
        data = DataBook()
        useCase = mock {
            given(it.execute("uic")).willReturn(Observable.just(some(data.domain.book)))
        }
    }

    private fun showFragment() {
        with(BookDetailFragment()) {
            viewModel = BookDetailsViewModel(useCase, AndroidResources(defaultContext.resources))
            arguments = bundle { putString(BOOK_UIC_NAVIGATION_ARG, "uic") }
            fragmentRule.setFragment(this)
        }
    }

    @Test
    fun showBookDetails() {
        // When
        showFragment()

        // Then
        assertDisplayed(R.id.book_title_view, data.ui.bookUi.title)
        assertDisplayed(R.id.book_author_view, data.ui.bookUi.author)
        assertDisplayed(R.id.book_summary_view, data.ui.bookUi.summary)
        assertDisplayed(R.id.book_distribution_view, data.ui.bookUi.distribution)
        assertDisplayed(R.id.book_pages_count_view, data.ui.bookUi.pagesCount)
    }


}