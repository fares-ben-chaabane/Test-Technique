package fr.benchaabane.presentationlayer.test

import android.os.Bundle
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.facebook.drawee.backends.pipeline.Fresco
import fr.benchaabane.presentationlayer.R

/**
 * Used as container to test fragments in isolation with Espresso
 */
@RestrictTo(RestrictTo.Scope.TESTS)
class TestFragmentActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_test_fragment)
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.content, fragment, TAG)
                .commit()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}

private const val TAG = "TEST"
