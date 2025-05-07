package alex.android.lab.app

import alex.android.lab.di.appModule
import alex.android.lab.di.dataModule
import alex.android.lab.di.domainModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }

}