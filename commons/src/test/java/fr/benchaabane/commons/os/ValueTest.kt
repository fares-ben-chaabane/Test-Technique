package fr.benchaabane.commons.os

import org.amshove.kluent.`should equal`
import org.junit.Test

class ValueTest {

    @Test
    fun `update cached value`() {
        // Given
        var updateValue: ((String) -> Unit)? = null
        val cachedValue = CachedValue<String> { setValue ->
            setValue("0")
            updateValue = { value -> setValue(value) }
        }
        // Then
        cachedValue.value `should equal` "0"
        updateValue!!("1")
        cachedValue.value `should equal` "1"
        updateValue!!("2")
        cachedValue.value `should equal` "2"
        updateValue!!("3")
        cachedValue.value `should equal` "3"
    }
}
