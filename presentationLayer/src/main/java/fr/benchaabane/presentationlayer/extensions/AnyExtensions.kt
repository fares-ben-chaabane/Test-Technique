package fr.benchaabane.presentationlayer.extensions

import fr.benchaabane.commons.tools.Logger


inline fun <reified T> Any.cast(): T? {
    if (this !is T) {
        Logger.e("$this should implement ${T::class.java}")
    }
    return this as? T
}
