package fr.benchaabane.presentationlayer.ui.splash

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class SplashView(lifeCycle: LifecycleOwner,
                 private val viewModel: SplashViewModel,
                 onBooksSaved: () -> Unit,
                 onErrorOccurred: () -> Unit) {

    private val errorObserver = Observer<Boolean> {
        if (it == true) {
            onErrorOccurred.invoke()
        }
    }
    private val successObserver = Observer<Boolean> {
        if (it == true) onBooksSaved.invoke() }

    init {
        viewModel.observeErrorState().observe(lifeCycle, errorObserver)
        viewModel.observeSuccessState().observe(lifeCycle, successObserver)
        viewModel.fetchBooks()
    }

    /* ------------------------------------- */
    /*              Processing               */
    /* ------------------------------------- */

    fun destroy() {
        viewModel.observeErrorState().removeObserver(errorObserver)
        viewModel.observeSuccessState().removeObserver(successObserver)
        viewModel.destroySelf()
    }

    fun retry() {
        viewModel.retry()
    }

}