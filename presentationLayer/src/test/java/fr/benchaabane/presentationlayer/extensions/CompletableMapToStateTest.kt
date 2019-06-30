package fr.benchaabane.presentationlayer.extensions

import io.reactivex.Completable
import org.junit.Test


class CompletableMapToStateTest {

    @Test
    fun `map success`() {
        // Given
        val completable = Completable.complete()
        // When
        val testObserver = completable.mapToState().test()
        // Then
        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isSuccess }
    }

    @Test
    fun `map error`() {
        // Given
        val completable = Completable.error(Exception())
        // When
        val testObserver = completable.mapToState().test()
        // Then
        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isError }
    }
}