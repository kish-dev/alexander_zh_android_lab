package alex.android.lab.app

import alex.android.lab.di.AppComponent
import alex.android.lab.di.AppModule
import alex.android.lab.di.DaggerAppComponent
import alex.android.lab.di.appModule
import alex.android.lab.di.dataModule
import alex.android.lab.di.domainModule
import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
    fun getAppComponent(): AppComponent = appComponent

}
