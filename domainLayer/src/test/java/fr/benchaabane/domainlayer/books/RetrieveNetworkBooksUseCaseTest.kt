package fr.benchaabane.domainlayer.books

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class RetrieveNetworkBooksUseCaseTest {

    private lateinit var repository: INetworkBooksRepository
    private lateinit var data: DataBook

    @Before
    fun setUp() {
        data = DataBook()
        repository = mock{
            given(it.retrieveBooks()).willReturn(Single.just(listOf(data.domain.book)))
        }
    }

    @Test
    fun `retrieve books`() {
        // Given
        val useCase = RetrieveNetworkBooksUseCase(repository)
        val expected = listOf(data.domain.book)

        // When
        val testObserver = useCase.execute().test()

        // Then
        testObserver.assertComplete()
            .assertNoErrors()
            .assertValue(expected)
            .assertValueCount(1)
    }


    @Test
    fun `retrieve books error`() {
        // Given
        given(repository.retrieveBooks()).willReturn(Single.error(Throwable()))
        val useCase = RetrieveNetworkBooksUseCase(repository)

        // When
        val testObserver = useCase.execute().test()

        // Then
        testObserver.assertNotComplete()
            .assertValueCount(0)

    }
}