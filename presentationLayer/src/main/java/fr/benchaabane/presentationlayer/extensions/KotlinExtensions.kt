package fr.benchaabane.presentationlayer.extensions

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

// Load lazy property
fun <T> T.forceLoad() = Unit
