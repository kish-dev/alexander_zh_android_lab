package alex.android.lab.di.modules.dataSourceModules

import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.data.DataSource.LocalDataSource.MainRoomDB
import alex.android.lab.data.DataSource.LocalDataSource.mappers.EntityMapper
import alex.android.lab.di.scopes.AppScope
import alex.android.lab.di.scopes.FeatureScope
import alex.android.lab.presentation.mappers.ProductListMapper
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    @AppScope
    fun provideDatabase(context: Context): MainRoomDB = MainRoomDB.getDB(context)

    @Provides
    @AppScope
    fun provideDao(database: MainRoomDB): Dao = database.getDao()
}