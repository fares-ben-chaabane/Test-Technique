package fr.benchaabane.test.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import fr.benchaabane.presentationlayer.tools.AndroidResources
import fr.benchaabane.presentationlayer.tools.Resources
import fr.benchaabane.presentationlayer.tools.Router
import fr.benchaabane.test.app.TestApplication
import javax.inject.Singleton


@Module
class AppModule(private val application: TestApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return Router()
    }

    @Provides
    @Singleton
    fun provideResources(): Resources {
        return AndroidResources(application.resources)
    }

}
