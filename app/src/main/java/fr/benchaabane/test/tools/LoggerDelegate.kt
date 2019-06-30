package fr.benchaabane.test.tools

import fr.benchaabane.commons.tools.Logger
import timber.log.Timber

class LoggerDelegate : Logger.Delegate {
    override fun log(message: String) {
        Timber.i(message)
    }

    override fun d(message: String) {
        Timber.d(message)
    }

    override fun e(throwable: Throwable?, message: String?) {
        Timber.e(throwable, message)
    }
}