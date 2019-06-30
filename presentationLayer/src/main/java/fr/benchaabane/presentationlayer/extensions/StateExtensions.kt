package fr.benchaabane.presentationlayer.extensions

import fr.benchaabane.commons.tools.Logger
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.presentationlayer.tools.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith


// Rx
fun <T> Observable<T>.mapToState(): Observable<State<T>> = map { item -> item.toState() }
        .startWith(loading())
        .doOnError { throwable -> Logger.e(throwable) }
        .onErrorResumeNext { throwable: Throwable -> Observable.just(throwable.toState()) }

fun <T> Observable<State<T>>.mapError(transform: (Throwable) -> Throwable): Observable<State<T>> = map { state ->
    if (state.isError) transform(state.asError().throwable).toState() else state
}

@Suppress("NAME_SHADOWING")
fun <T> Observable<T>.mapToStateWithRetry(retry: Observable<*>): Observable<State<T>> = map { item -> item.toState() }
        .startWith(loading())
        .doOnError { throwable -> Logger.e(throwable) }
        .onErrorResumeNext { throwable: Throwable ->
            Observable.just<State<T>>(throwable.toState())
                    .concatWith(Observable.error(throwable))
        }
        .retryWhen { error -> error.zipWith(retry) { error: Throwable, _ -> error } }

fun <T> Observable<T>.subscribeAsyncToState(onSuccess: (T) -> Unit = {},
                                            onError: (Throwable) -> Unit = {},
                                            onLoading: () -> Unit = {}): Disposable = mapToState()
        .subscribeAsync(onSuccess, onError, onLoading)

fun <T> Observable<T>.subscribeAsyncToStateWithRetry(retry: Observable<*>,
                                                     onSuccess: (T) -> Unit = {},
                                                     onError: (Throwable) -> Unit = {},
                                                     onLoading: () -> Unit = {}): Disposable = mapToStateWithRetry(retry)
        .subscribeAsync(onSuccess, onError, onLoading)

@Suppress("NAME_SHADOWING")
fun Completable.mapToStateWithRetry(retry: Observable<*>): Observable<State<Unit>> = toObservable<State<Unit>>()
        .startWith(loading())
        .mergeWith(Observable.just(Unit.toState()))
        .doOnError { throwable -> Logger.e(throwable) }
        .onErrorResumeNext { throwable: Throwable ->
            Observable.just<State<Unit>>(throwable.toState())
                    .concatWith(Observable.error(throwable))
        }
        .retryWhen { error -> error.zipWith(retry) { error: Throwable, _ -> error } }


fun Completable.mapToState(): Observable<State<Unit>> = toObservable<State<Unit>>()
        .startWith(loading())
        .mergeWith(Observable.just(Unit.toState()))
        .doOnError { throwable -> Logger.e(throwable) }
        .onErrorResumeNext { throwable: Throwable -> Observable.just(throwable.toState()) }

fun Completable.subscribeAsyncToState(onSuccess: () -> Unit = {},
                                      onError: (Throwable) -> Unit = {},
                                      onLoading: () -> Unit = {}): Disposable = mapToState()
        .subscribeAsync({ onSuccess() }, onError, onLoading)

fun Completable.subscribeAsyncToStateWithRetry(retry: Observable<*>,
                                               onSuccess: () -> Unit = {},
                                               onError: (Throwable) -> Unit = {},
                                               onLoading: () -> Unit = {}): Disposable = mapToStateWithRetry(retry)
        .subscribeAsync({ onSuccess() }, onError, onLoading)

fun <T> Observable<State<T>>.subscribeAsync(onSuccess: (T) -> Unit = {},
                                            onError: (Throwable) -> Unit = {},
                                            onLoading: () -> Unit = {}): Disposable = subscribeOn(TestSchedulers.computation)
        .observeOn(TestSchedulers.ui)
        .subscribe({ state ->
                       when {
                           state.isLoading -> onLoading()
                           state.isSuccess -> onSuccess(state.asSuccess().data)
                           state.isError -> onError(state.asError().throwable)
                       }
                   },
                   { onError(it) })
