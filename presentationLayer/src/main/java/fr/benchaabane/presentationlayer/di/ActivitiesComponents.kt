package fr.benchaabane.presentationlayer.di

import dagger.Subcomponent
import fr.benchaabane.presentationlayer.ui.BaseActivity
import fr.benchaabane.presentationlayer.ui.splash.SplashActivity

@Subcomponent
interface ActivitiesComponents {
    fun inject(activity: BaseActivity)
    fun inject(activity: SplashActivity)
}