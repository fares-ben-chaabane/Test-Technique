package fr.benchaabane.presentationlayer.extensions

import fr.benchaabane.presentationlayer.tools.asSuccess
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test


class ObservableMapToStateTest {

    private lateinit var observable: PublishSubject<Int>

    @Before
    fun setUp() {
        observable = PublishSubject.create<Int>()
    }

    @Test
    fun `init with loading`() {
        // When
        val testObserver = observable.mapToState().test()
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue { it.isLoading }
    }

    @Test
    fun `map success`() {
        // Given
        val testObserver = observable.mapToState().test()
        // When
        observable.onNext(1)
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isSuccess && it.asSuccess().data == 1 }
    }

    @Test
    fun `many events`() {
        // Given
        val testObserver = observable.mapToState().test()
        // When
        observable.onNext(1)
        observable.onNext(2)
        observable.onNext(3)
        // Then
        testObserver.assertNotComplete()
                .assertNoErrors()
                .assertValueCount(4)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isSuccess && it.asSuccess().data == 1 }
                .assertValueAt(2) { it.isSuccess && it.asSuccess().data == 2 }
                .assertValueAt(3) { it.isSuccess && it.asSuccess().data == 3 }
    }

    @Test
    fun `map error`() {
        // Given
        val testObserver = observable.mapToState().test()
        // When
        observable.onError(Exception())
        // Then
        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0) { it.isLoading }
                .assertValueAt(1) { it.isError }
    }
}