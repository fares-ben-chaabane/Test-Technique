package fr.benchaabane.commons.tools

object Logger {
    private var delegate: Delegate = NoOpDelegate()

    fun log(message: String) = delegate.log(message)
    fun d(message: String) = delegate.d(message)
    fun e(message: String) = delegate.e(message = message)
    fun e(throwable: Throwable) = delegate.e(throwable = throwable)
    fun e(throwable: Throwable, message: String) = delegate.e(throwable, message)

    fun use(delegate: Delegate) {
        Logger.delegate = delegate
    }

    interface Delegate {
        fun log(message: String)
        fun d(message: String)
        fun e(throwable: Throwable? = null, message: String? = null)
    }
}

private class NoOpDelegate : Logger.Delegate {
    override fun log(message: String) = Unit
    override fun e(throwable: Throwable?, message: String?) = Unit
    override fun d(message: String) = Unit
}