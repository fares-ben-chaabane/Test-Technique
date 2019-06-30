package fr.benchaabane.presentationlayer.test


import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.commons_android.tools.initForAndroidTest
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseTest {

    val defaultContext: Context
        get() = InstrumentationRegistry.getTargetContext()

    @Before
    fun setUpBeforeTest() {
        TestSchedulers.initForAndroidTest()
    }

}


