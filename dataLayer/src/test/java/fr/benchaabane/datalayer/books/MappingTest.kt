package fr.benchaabane.datalayer.books

import fr.benchaabane.domainlayer.books.BookCategory
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not equal`
import org.junit.Before
import org.junit.Test

class MappingTest {

    private lateinit var data: DataBook

    @Before
    fun setUp() {
        data = DataBook()
    }

    @Test
    fun `map book`() {
        // Given
        val expected = data.domain.book

        // When
        val book = data.json.bookJson.toBook()

        // Then
        book `should equal` expected
    }

    @Test
    fun `wrong book map`() {
        // Given
        val expected = data.domain.book.copy(category = BookCategory.FICTION)

        // When
        // When
        val book = data.json.bookJson.toBook()

        // Then
        book `should not equal` expected
    }
}