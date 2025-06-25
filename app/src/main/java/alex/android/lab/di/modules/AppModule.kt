package alex.android.lab.di.modules

import alex.android.lab.app.App
import alex.android.lab.di.scopes.AppScope
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    @AppScope
    fun provideApplicationContext(): Context = application
}