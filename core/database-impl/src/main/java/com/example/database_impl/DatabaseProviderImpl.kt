package com.example.database_impl

import android.content.Context
import com.example.database_api.DatabaseApi
import com.example.database_api.DatabaseProvider
import com.example.database_impl.di.DaggerDatabaseComponent
import com.example.database_impl.di.DatabaseComponent

class DatabaseProviderImpl(context: Context) : DatabaseProvider {

    private val databaseComponent: DatabaseComponent = DaggerDatabaseComponent.factory()
        .create(context)

    override fun provideDatabaseApi(): DatabaseApi =
        databaseComponent.databaseApi()
}