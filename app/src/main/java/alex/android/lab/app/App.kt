package alex.android.lab.app

import alex.android.lab.di.AppComponent
import alex.android.lab.di.DaggerAppComponent
import android.app.Application

class App : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(this)
    }

    fun getAppComponent(): AppComponent = appComponent
}

