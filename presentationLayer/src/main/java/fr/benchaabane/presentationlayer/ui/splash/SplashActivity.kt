package fr.benchaabane.presentationlayer.ui.splash

import android.os.Bundle
import fr.benchaabane.presentationlayer.R
import fr.benchaabane.presentationlayer.di.injector
import fr.benchaabane.presentationlayer.tools.goTo
import fr.benchaabane.presentationlayer.ui.BaseActivity
import fr.benchaabane.presentationlayer.ui.DialogCallbacks
import fr.benchaabane.presentationlayer.ui.books.BooksActivity
import fr.benchaabane.presentationlayer.ui.showDialog
import javax.inject.Inject

class SplashActivity : BaseActivity(R.layout.activity_splash), DialogCallbacks {

    @Inject lateinit var viewModel: SplashViewModel
    private var splashView: SplashView? = null

    /* ------------------------------------- */
    /*               Lifecycle               */
    /* ------------------------------------- */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector?.inject(this)
        splashView = SplashView(lifeCycle = this,
                                viewModel = viewModel,
                                onBooksSaved = { proceedToBookList() },
                                onErrorOccurred = { showDialog(message = getString(R.string.error_message),
                                    title = getString(R.string.error_title), id = ERROR_DIALOG_ID, isCancelable = false,
                                    negativeLabel = getString(android.R.string.ok), positiveLabel = getString(R.string.error_retry)) })
    }

    override fun onDestroy() {
        splashView?.destroy()
        splashView = null
        super.onDestroy()
    }


    /* ------------------------------------- */
    /*               Delegate                */
    /* ------------------------------------- */

    override fun onDialogNegative(id: String) {
        super.onDialogNegative(id)
        if (id == ERROR_DIALOG_ID) finish()
    }

    override fun onDialogPositive(id: String) {
        super.onDialogPositive(id)
        if (id == ERROR_DIALOG_ID) splashView?.retry()
    }


    /* ------------------------------------- */
    /*              Processing               */
    /* ------------------------------------- */

    private fun proceedToBookList() {
        goTo(BooksActivity.newIntent(this))
        finish()
    }
}
private const val ERROR_DIALOG_ID = "ERROR_DIALOG_ID"