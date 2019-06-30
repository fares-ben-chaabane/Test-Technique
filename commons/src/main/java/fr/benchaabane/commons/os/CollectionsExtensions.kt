package fr.benchaabane.commons.os


fun <T : Comparable<T>> List<T>.equalsIgnoringOrder(list: List<T>): Boolean = sorted() == list.sorted()

fun <T> List<T>.secondOrNull(): T? = getOrNull(1)
