package fr.benchaabane.presentationlayer.test


import androidx.fragment.app.Fragment
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class TestFragmentRule private constructor(initialTouchMode: Boolean,
                                              launchActivity: Boolean) : TestRule {

    private val activityTestRule = ActivityTestRule(TestFragmentActivity::class.java, initialTouchMode, launchActivity)

    private val activity: TestFragmentActivity?
        get() = activityTestRule.activity

    override fun apply(base: Statement, description: Description): Statement {
        return RuleChain.outerRule(activityTestRule)
                // ↓ All rules below flakyTestRule will be repeated
                .apply(base, description)
    }

    fun setFragment(fragment: Fragment) = activity?.setFragment(fragment).also {
        BaristaSleepInteractions.sleep(100)
    }

    companion object {
        fun create(initialTouchMode: Boolean = INITIAL_TOUCH_MODE_ENABLED,
                   launchActivity: Boolean = LAUNCH_ACTIVITY_AUTOMATICALLY) = TestFragmentRule(initialTouchMode, launchActivity)
    }
}

const val DEFAULT_FLAKY_ATTEMPTS = 3
const val LAUNCH_ACTIVITY_AUTOMATICALLY = true
const val INITIAL_TOUCH_MODE_ENABLED = false
