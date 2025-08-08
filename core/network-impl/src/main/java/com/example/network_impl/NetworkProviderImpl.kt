package com.example.network_impl

import android.content.Context
import com.example.network_api.NetworkProvider
import com.example.network_api.remote.RemoteDataSourceApi
import com.example.network_impl.di.DaggerNetworkComponent
import com.example.network_impl.di.NetworkComponent

class NetworkProviderImpl(baseUrl: String) : NetworkProvider {
    private val component: NetworkComponent = DaggerNetworkComponent.factory()
        .create()

    override fun provideRemoteDataSourceApi(): RemoteDataSourceApi =
        component.remoteDataSourceApi
}