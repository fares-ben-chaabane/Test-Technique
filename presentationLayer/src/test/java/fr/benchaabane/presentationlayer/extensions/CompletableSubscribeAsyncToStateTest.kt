package fr.benchaabane.presentationlayer.extensions



import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.times
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.commons_android.tools.initForTest
import io.reactivex.Completable
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class CompletableSubscribeAsyncToStateTest {

    private lateinit var onSuccess: () -> Unit
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
    fun `on success`() {
        // Given
        val completable = Completable.complete()
        // When
        completable.subscribeAsyncToState(onSuccess = onSuccess,
                                          onLoading = onLoading,
                                          onError = onError)
        // Then
        then(onSuccess).should(times(1)).invoke()
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(0)).invoke(any())
    }

    @Test
    fun `on error`() {
        // Given
        val completable = Completable.error(Exception())
        // When
        completable.subscribeAsyncToState(onSuccess = onSuccess,
                                          onLoading = onLoading,
                                          onError = onError)
        // Then
        then(onSuccess).should(times(0)).invoke()
        then(onLoading).should(times(1)).invoke()
        then(onError).should(times(1)).invoke(any())
    }
}