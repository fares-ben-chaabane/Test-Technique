package fr.benchaabane.commons.os

import org.amshove.kluent.`should be`
import org.junit.Test

class CollectionsExtensionsTest {

    @Test
    fun `lists are equal when ignoring order`() {
        // Given
        val list1 = listOf("a", "b", "c", "d", "a")
        val list2 = listOf("c", "d", "a", "a", "b")
        // When
        val listsAreEqual = list1.equalsIgnoringOrder(list2)
        // Then
        listsAreEqual `should be` true
    }

    @Test
    fun `lists are definitively not equal`() {
        // Given
        val list1 = listOf("a", "b", "b")
        val list2 = listOf("a", "a", "b")
        // When
        val listsAreEqual = list1.equalsIgnoringOrder(list2)
        // Then
        listsAreEqual `should be` false
    }
}