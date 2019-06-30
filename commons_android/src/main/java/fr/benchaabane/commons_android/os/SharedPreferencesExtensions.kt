package fr.benchaabane.commons_android.os

import android.content.SharedPreferences


fun SharedPreferences.getString(key: String): String? = getString(key, null)
fun SharedPreferences.getBoolean(key: String): Boolean? = if (contains(key)) getBoolean(key, true) else null

fun SharedPreferences.Editor.updateString(key: String, value: String?) {
    value?.let { putString(key, it) } ?: remove(key)
}

fun SharedPreferences.Editor.updateBoolean(key: String, value: Boolean?) {
    value?.let { putBoolean(key, it) } ?: remove(key)
}

inline fun SharedPreferences.commit(action: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        action()
        commit()
    }
}

inline fun SharedPreferences.apply(action: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        action()
        apply()
    }
}

fun SharedPreferences.clearAll() = commit { clear() }
