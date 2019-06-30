package fr.benchaabane.commons.os

interface Value<out T> {
    val value: T
}

class CachedValue<T : Any>(init: ((T) -> Unit) -> Unit) : Value<T> {
    override lateinit var value: T
        private set

    init {
        init { value = it }
    }
}