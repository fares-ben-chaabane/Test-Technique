package fr.benchaabane.presentationlayer.ui

import androidx.fragment.app.Fragment
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.times
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotContains
import com.schibsted.spain.barista.interaction.BaristaDialogInteractions.clickDialogNegativeButton
import com.schibsted.spain.barista.interaction.BaristaDialogInteractions.clickDialogPositiveButton
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep
import fr.benchaabane.presentationlayer.test.BaseTest
import fr.benchaabane.presentationlayer.test.TestFragmentRule
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BaseDialogFragmentTest : BaseTest() {

    @Rule
    @JvmField
    var fragmentRule = TestFragmentRule.create()

    private lateinit var dialogCallbacksMock: DialogCallbacks
    private lateinit var fragment: Fragment

    @Before
    fun setUp() {
        dialogCallbacksMock = mock()
    }

    private fun showFragment(fragment: Fragment = TestFragment(dialogCallbacksMock)) {
        this.fragment = fragment
        fragmentRule.setFragment(fragment)
    }

    private fun findDialogFragment(): BaseDialogFragment {
        return fragment.fragmentManager!!.findFragmentByTag(TAG) as BaseDialogFragment
    }

    @Test
    fun showDialog() {
        // Given
        showFragment()
        // When
        fragment.showDialog(title = "title",
                            message = "message",
                            positiveLabel = "positive",
                            negativeLabel = "negative")
        // Then
        assertDisplayed("title")
        assertDisplayed("message")
        assertDisplayed("positive")
        assertDisplayed("negative")
    }

    @Test
    fun handlePositiveButtonWithoutId() {
        // Given
        showFragment()
        fragment.showDialog(message = "message",
                            positiveLabel = "positive")
        // When
        clickDialogPositiveButton()
        // Then
        then(dialogCallbacksMock).should(times(0)).onDialogPositive(any())
        then(dialogCallbacksMock).should(times(0)).onDialogNegative(any())
        then(dialogCallbacksMock).should(times(0)).onDialogDismiss(any())
        then(dialogCallbacksMock).should(times(0)).onDialogCancel(any())
        assertNotContains("message")
    }

    @Test
    fun handlePositiveButtonWithId() {
        // Given
        showFragment()
        fragment.showDialog(id = "id",
                            message = "message",
                            positiveLabel = "positive")
        // When
        clickDialogPositiveButton()
        // Then
        then(dialogCallbacksMock).should(times(1)).onDialogPositive("id")
        then(dialogCallbacksMock).should(times(0)).onDialogNegative(any())
        then(dialogCallbacksMock).should(times(1)).onDialogDismiss(any())
        then(dialogCallbacksMock).should(times(0)).onDialogCancel(any())
        assertNotContains("message")
    }


    @Test
    fun handleNegativeButtonWithoutId() {
        // Given
        showFragment()
        fragment.showDialog(message = "message",
                            negativeLabel = "negative")
        // When
        clickDialogNegativeButton()
        // Then
        then(dialogCallbacksMock).should(times(0)).onDialogPositive(any())
        then(dialogCallbacksMock).should(times(0)).onDialogNegative(any())
        then(dialogCallbacksMock).should(times(0)).onDialogDismiss(any())
        then(dialogCallbacksMock).should(times(0)).onDialogCancel(any())
        assertNotContains("message")
    }

    @Test
    fun handleNegativeButtonWithId() {
        // Given
        showFragment()
        fragment.showDialog(id = "id",
                            message = "message",
                            negativeLabel = "negative")
        // When
        clickDialogNegativeButton()
        // Then
        then(dialogCallbacksMock).should(times(0)).onDialogPositive(any())
        then(dialogCallbacksMock).should(times(1)).onDialogNegative("id")
        then(dialogCallbacksMock).should(times(1)).onDialogDismiss("id")
        then(dialogCallbacksMock).should(times(0)).onDialogCancel(any())
        assertNotContains("message")
    }

    @Test
    fun handleDismiss() {
        // Given
        showFragment()
        fragment.showDialog(id = "id",
                            message = "message")
        sleep(100)
        // When
        findDialogFragment().dismiss()
        sleep(100)
        // Then
        then(dialogCallbacksMock).should(times(1)).onDialogDismiss("id")
    }

    @Test
    fun handleCancel() {
        // Given
        showFragment()
        fragment.showDialog(id = "id",
                            message = "message")
        sleep(100)
        // When
        findDialogFragment().dialog.cancel()
        sleep(100)
        // Then
        then(dialogCallbacksMock).should(times(1)).onDialogCancel("id")
        then(dialogCallbacksMock).should(times(1)).onDialogDismiss("id")
    }
}

class TestFragment(private val dialogCallbacksDelegate: DialogCallbacks) : Fragment(), DialogCallbacks {

    override fun onDialogCancel(id: String) {
        dialogCallbacksDelegate.onDialogCancel(id)
    }

    override fun onDialogDismiss(id: String) {
        dialogCallbacksDelegate.onDialogDismiss(id)
    }

    override fun onDialogNegative(id: String) {
        dialogCallbacksDelegate.onDialogNegative(id)
    }

    override fun onDialogPositive(id: String) {
        dialogCallbacksDelegate.onDialogPositive(id)
    }
}