package com.example.database_impl.di

import android.content.Context
import com.example.database_api.DatabaseApi
import com.example.database_api.mappers.Mapper
import com.example.database_impl.Dao
import com.example.database_impl.DatabaseImpl
import com.example.database_impl.MainRoomDB
import com.example.database_impl.MapperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MainRoomDB {
        return MainRoomDB.getDB(context)
    }

    @Provides
    @Singleton
    fun provideDao(database: MainRoomDB): Dao {
        return database.getDao()
    }

    @Provides
    @Singleton
    fun provideEntityMapper(): Mapper {
        return MapperImpl()
    }

    @Provides
    fun provideDatabaseApi(dao: Dao, mapper: Mapper): DatabaseApi {
        return DatabaseImpl(dao, mapper)
    }
}


