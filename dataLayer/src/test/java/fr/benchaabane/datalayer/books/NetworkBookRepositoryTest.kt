package fr.benchaabane.datalayer.books


import com.nhaarman.mockitokotlin2.mock
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.commons_android.tools.initForTest
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given

class NetworkBookRepositoryTest {

    private lateinit var apiService: BooksApi
    private lateinit var data: DataBook

    @Before
    fun setUp() {
        TestSchedulers.initForTest()
        data = DataBook()
        apiService = mock {
            given(it.getBooks()).willReturn(Single.just(data.json.bookJsonWrapper))
            given(it.updateBook("uic", data.json.bookUpdateJson)).willReturn(Completable.complete())
        }
    }

    @Test
    fun `retrieve books`() {
        // Given
        val repository = NetworkBooksRepository(apiService)
        val expected = listOf(data.domain.book, data.domain.book.copy(uic = "2", rate = 4))

        // When
        val testObserver = repository.retrieveBooks().test()

        // Then
        testObserver.assertComplete()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(expected)
    }


    @Test
    fun `update book`() {
        // Given
        val repository = NetworkBooksRepository(apiService)

        // When
        val testObserver = repository.updateBook(data.domain.bookUpdate).test()

        // Then
        testObserver.assertComplete()
            .assertNoErrors()
    }
}