package fr.benchaabane.commons_android.os

import android.util.SparseArray


// SparseArray
inline fun <T> Iterable<T>.toSparseArray(keySelector: (T) -> Int): SparseArray<T> {
    return toSparseArray(SparseArray(10), keySelector)
}

inline fun <T, V> Iterable<T>.toSparseArray(keySelector: (T) -> Int, transform: (T) -> V): SparseArray<V> {
    return toSparseArray(SparseArray(10), keySelector, transform)
}

inline fun <T> Iterable<T>.toSparseArray(destination: SparseArray<T>, keySelector: (T) -> Int): SparseArray<T> {
    for (element in this) {
        destination.put(keySelector(element), element)
    }
    return destination
}

inline fun <T, V> Iterable<T>.toSparseArray(destination: SparseArray<V>, keySelector: (T) -> Int, transform: (T) -> V): SparseArray<V> {
    for (element in this) {
        destination.put(keySelector(element), transform(element))
    }
    return destination
}

