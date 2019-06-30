package fr.benchaabane.test.di.components

import dagger.Component
import fr.benchaabane.presentationlayer.di.ActivitiesComponents
import fr.benchaabane.presentationlayer.di.AppComponent
import fr.benchaabane.presentationlayer.di.FragmentsComponents
import fr.benchaabane.test.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoriesModule::class, UseCasesModule::class,
    ViewModelsModule::class])
interface TestAppComponent : AppComponent