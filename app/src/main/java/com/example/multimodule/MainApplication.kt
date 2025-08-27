package com.example.multimodule

import android.app.Application
import com.example.database_api.DatabaseApi
import com.example.database_impl.di.DatabaseComponent
import com.example.multimodule.di.AppComponent
import com.example.multimodule.di.ComponentHolderInitializer
import com.example.multimodule.di.DaggerAppComponent
import com.example.navigation_impl.NavigationActivityProvider
import com.example.network_impl.di.NetworkComponent
import javax.inject.Inject

internal class MainApplication : Application() {

    @Inject
    lateinit var componentHolderInitializer: ComponentHolderInitializer

    private val appComponent: AppComponent
        get() = DaggerAppComponent.factory().create(
        activityProvider = NavigationActivityProvider(this),
    )


    override fun onCreate() {
        super.onCreate()

        NetworkComponent.init()
        DatabaseComponent.init(this)

        appComponent.inject(this)
        componentHolderInitializer.init()
    }
}