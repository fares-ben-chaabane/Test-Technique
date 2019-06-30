package fr.benchaabane.presentationlayer.extensions


import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.times
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.commons_android.tools.initForTest
import io.reactivex.subjects.PublishSubject
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class ObservableSubscribeAsyncToStateTest {

    private lateinit var onSuccess: (String) -> Unit
    private lateinit var onLoading: () -> Unit
    private lateinit var onError: (Throwable) -> Unit

    @Before
    fun setUp() {
        TestSchedulers.initForTest()
        onSuccess = mock()
        onLoading = mock()
        onError = mock()
    }

    @Test
    fun `on loading`() {
        // Given
        val publish = PublishSubject.create<String>()
        // When
        publish.subscribeAsyncToState(onSuccess = onSuccess,
                                      onLoading = onLoading,
                                      onError = onError)
        // Then
        then(onSuccess).should(times(0)).invoke(any())
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(0)).invoke(any())
    }

    @Test
    fun `on success`() {
        // Given
        val publish = PublishSubject.create<String>()
        publish.subscribeAsyncToState(onSuccess = onSuccess,
                                      onLoading = onLoading,
                                      onError = onError)
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
        publish.subscribeAsyncToState(onSuccess = onSuccess,
                                      onLoading = onLoading,
                                      onError = onError)
        then(onLoading).should(times(1)).invoke()
        // When
        publish.onError(Exception())
        // Then
        then(onSuccess).should(times(0)).invoke(any())
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(1)).invoke(any())
    }
}
