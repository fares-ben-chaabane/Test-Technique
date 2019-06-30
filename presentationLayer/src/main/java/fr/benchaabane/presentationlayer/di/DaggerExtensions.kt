package fr.benchaabane.presentationlayer.di

import android.app.Activity
import androidx.fragment.app.Fragment


val Activity.injector: ActivitiesComponents?
    get() = (applicationContext as? AppComponent)?.activitiesComponent()

val Fragment.injector: FragmentsComponents?
    get() = (context?.applicationContext as? AppComponent)?.fragmentsComponent()