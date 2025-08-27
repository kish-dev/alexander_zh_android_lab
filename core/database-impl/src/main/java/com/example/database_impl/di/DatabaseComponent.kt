package com.example.database_impl.di

import android.content.Context
import com.example.database_api.DatabaseApi
import com.example.database_api.DatabaseProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DatabaseModule::class]
)
interface DatabaseComponent: DatabaseProvider {
    fun databaseApi(): DatabaseApi

    override fun provideDatabaseApi(): DatabaseApi

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DatabaseComponent
    }

    companion object {
        private var instance: DatabaseComponent? = null

        fun init(context: Context) {
            instance = DaggerDatabaseComponent.factory().create(context)
        }

        fun get(): DatabaseComponent = instance
            ?: throw IllegalStateException("DatabaseComponent not initialized")
    }
}