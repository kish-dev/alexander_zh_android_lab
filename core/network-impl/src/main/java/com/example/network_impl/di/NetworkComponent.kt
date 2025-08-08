package com.example.network_impl.di

import android.content.Context
import com.example.network_api.NetworkProvider
import com.example.network_impl.NetworkDependencies
import com.example.network_api.remote.RemoteDataSourceApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent : NetworkProvider{
    val remoteDataSourceApi: RemoteDataSourceApi

    override fun provideRemoteDataSourceApi(): RemoteDataSourceApi

    @Component.Factory
    interface Factory {
        fun create(): NetworkComponent
    }

    companion object  {
        private var instance: NetworkComponent? = null

        fun init() {
            instance = DaggerNetworkComponent.factory().create()
        }

        fun get(): NetworkComponent = instance
            ?: throw IllegalStateException("NetworkComponent not initialized")

    }
}