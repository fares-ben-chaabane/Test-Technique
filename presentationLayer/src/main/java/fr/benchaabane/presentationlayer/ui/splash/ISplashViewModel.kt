package fr.benchaabane.presentationlayer.ui.splash

import androidx.lifecycle.LiveData

interface ISplashViewModel {

    fun observeErrorState(): LiveData<Boolean>
    fun observeSuccessState(): LiveData<Boolean>

    fun fetchBooks()
    fun retry()
    fun destroySelf()
}