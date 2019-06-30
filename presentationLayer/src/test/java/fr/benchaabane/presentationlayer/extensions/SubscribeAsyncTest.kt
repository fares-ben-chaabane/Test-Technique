package fr.benchaabane.presentationlayer.extensions

import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.times
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.commons_android.tools.initForTest
import fr.benchaabane.presentationlayer.tools.State
import io.reactivex.Observable
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class SubscribeAsyncTest {

    private lateinit var onLoadingMock: () -> Unit
    private lateinit var onSuccessMock: (String) -> Unit
    private lateinit var onErrorMock: (Throwable) -> Unit

    @Before
    fun setUp() {
        TestSchedulers.initForTest()
        onLoadingMock = mock()
        onSuccessMock = mock()
        onErrorMock = mock()
    }

    @Test
    fun `on success`() {
        // Given
        val observable: Observable<State<String>> = Observable.just(State.Success("item"))
        // When
        observable.subscribeAsync(onSuccess = onSuccessMock,
                                  onLoading = onLoadingMock,
                                  onError = onErrorMock)
        // Then
        then(onSuccessMock).should(times(1)).invoke(any())
        then(onLoadingMock).should(times(0)).invoke()
        then(onErrorMock).should(times(0)).invoke(any())
    }

    @Test
    fun `on loading`() {
        // Given
        val observable: Observable<State<String>> = Observable.just(State.Loading())
        // When
        observable.subscribeAsync(onSuccess = onSuccessMock,
                                  onLoading = onLoadingMock,
                                  onError = onErrorMock)
        // Then
        then(onSuccessMock).should(times(0)).invoke(any())
        then(onLoadingMock).should(times(1)).invoke()
        then(onErrorMock).should(times(0)).invoke(any())
    }

    @Test
    fun `on error`() {
        // Given
        val observable: Observable<State<String>> = Observable.just(State.Error(Exception()))
        // When
        observable.subscribeAsync(onSuccess = onSuccessMock,
                                  onLoading = onLoadingMock,
                                  onError = onErrorMock)
        // Then
        then(onSuccessMock).should(times(0)).invoke(any())
        then(onLoadingMock).should(times(0)).invoke()
        then(onErrorMock).should(times(1)).invoke(any())
    }
}