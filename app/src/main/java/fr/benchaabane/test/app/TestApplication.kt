package fr.benchaabane.test.app

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import com.jakewharton.threetenabp.AndroidThreeTen
import fr.benchaabane.commons.tools.Logger
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.presentationlayer.di.ActivitiesComponents
import fr.benchaabane.presentationlayer.di.FragmentsComponents
import fr.benchaabane.test.BuildConfig
import fr.benchaabane.test.di.components.DaggerTestAppComponent
import fr.benchaabane.test.di.components.TestAppComponent
import fr.benchaabane.test.di.modules.AppModule
import fr.benchaabane.test.tools.LoggerDelegate
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class TestApplication : Application(), TestAppComponent {

    private lateinit var appComponent: TestAppComponent

    /* ------------------------------------- */
    /*               LifeCycle               */
    /* ------------------------------------- */

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initDaggerGraph()
        initSchedulers()
        initRealmDatabase()
        initFresco()
        initThreeTen()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level != ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            val imagePipeline = Fresco.getImagePipeline()
            imagePipeline.clearCaches()
        }
    }

    override fun onTerminate() {
        Realm.getDefaultInstance().close()
        super.onTerminate()
    }


    private fun initDaggerGraph() {
        appComponent = DaggerTestAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }


    /* ------------------------------------- */
    /*             AppComponent              */
    /* ------------------------------------- */

    override fun fragmentsComponent(): FragmentsComponents = appComponent.fragmentsComponent()
    override fun activitiesComponent(): ActivitiesComponents = appComponent.activitiesComponent()
}

/* ------------------------------------- */
/*              Processing               */
/* ------------------------------------- */

private fun initLogger() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
    Logger.use(LoggerDelegate())
}

private fun initSchedulers() {
    TestSchedulers.init()
}

private fun Context.initFresco() {
    Fresco.initialize(this)
}

private fun Context.initThreeTen() {
    AndroidThreeTen.init(this)
}

private fun Context.initRealmDatabase() {
    Realm.init(this)
    Realm.setDefaultConfiguration(RealmConfiguration.Builder()
        .name("testDB.realm")
        .schemaVersion(1)
        .build())
}