package fr.benchaabane.commons.os

import java.util.NoSuchElementException

data class Optional<out T>(val value: T? = null) {
    fun get(): T = value ?: throw NoSuchElementException()
    val isPresent = value != null
}

private val EMPTY = Optional<Nothing>()

fun <T> empty(): Optional<T> = EMPTY
fun <T> some(value: T) = Optional(value)
inline fun <T> Optional<T>.ifPresent(action: (T) -> Unit) {
    if (isPresent) action(get())
}

inline fun <T, U> Optional<T>.map(action: (T) -> U) = Optional(value?.let { action(it) })