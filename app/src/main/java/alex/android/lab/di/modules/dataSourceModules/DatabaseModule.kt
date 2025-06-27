package alex.android.lab.di.modules.dataSourceModules

import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.data.DataSource.LocalDataSource.MainRoomDB
import alex.android.lab.di.scopes.AppScope
import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    @AppScope
    fun provideDatabase(application: Application): MainRoomDB = MainRoomDB.getDB(application)

    @Provides
    @AppScope
    fun provideDao(database: MainRoomDB): Dao = database.getDao()
}