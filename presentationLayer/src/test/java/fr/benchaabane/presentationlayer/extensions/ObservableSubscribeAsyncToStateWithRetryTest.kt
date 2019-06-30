package fr.benchaabane.presentationlayer.extensions


import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.times
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.commons_android.tools.initForTest
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.subjects.PublishSubject
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Callable

class ObservableSubscribeAsyncToStateWithRetryTest {

    private lateinit var onSuccess: (String) -> Unit
    private lateinit var onLoading: () -> Unit
    private lateinit var onError: (Throwable) -> Unit
    private lateinit var retry: PublishSubject<Unit>

    @Before
    fun setUp() {
        TestSchedulers.initForTest()
        onSuccess = mock()
        onLoading = mock()
        onError = mock()
        retry = PublishSubject.create()
    }

    @Test
    fun `on loading`() {
        // Given
        val publish = PublishSubject.create<String>()
        // When
        publish.subscribeAsyncToStateWithRetry(onSuccess = onSuccess,
                                               onLoading = onLoading,
                                               onError = onError,
                                               retry = retry)
        // Then
        then(onSuccess).should(times(0)).invoke(any())
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(0)).invoke(any())
    }

    @Test
    fun `on success`() {
        // Given
        val publish = PublishSubject.create<String>()
        publish.subscribeAsyncToStateWithRetry(onSuccess = onSuccess,
                                               onLoading = onLoading,
                                               onError = onError,
                                               retry = retry)
        then(onLoading).should(times(1)).invoke()
        // When
        publish.onNext("first")
        // Then
        then(onSuccess).should(times(1)).invoke("first")
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(0)).invoke(any())
    }

    @Test
    fun `on error`() {
        // Given
        val publish = PublishSubject.create<String>()
        publish.subscribeAsyncToStateWithRetry(onSuccess = onSuccess,
                                               onLoading = onLoading,
                                               onError = onError,
                                               retry = retry)
        then(onLoading).should(times(1)).invoke()
        // When
        publish.onError(Exception())
        // Then
        then(onSuccess).should(times(0)).invoke(any())
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(1)).invoke(any())
    }

    @Test
    fun `loading when retry`() {
        // Given
        val publish = PublishSubject.create<String>()
        val deferPublish = Observable.defer(object : Callable<ObservableSource<String>> {
            var callCount = 0
            override fun call(): ObservableSource<String> {
                callCount++
                return when (callCount) {
                    1 -> Observable.error(Exception())
                    else -> publish
                }
            }
        })
        deferPublish.subscribeAsyncToStateWithRetry(onSuccess = onSuccess,
                                                    onLoading = onLoading,
                                                    onError = onError,
                                                    retry = retry)
        // When
        retry.onNext(Unit)
        publish.onNext("first")
        // Then
        then(onSuccess).should(times(1)).invoke("first")
        then(onLoading).should(times(2)).invoke()
        then(onError).should(times(1)).invoke(any())
    }


}