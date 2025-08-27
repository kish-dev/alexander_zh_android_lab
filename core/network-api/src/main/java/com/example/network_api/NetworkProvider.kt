package com.example.network_api

import com.example.network_api.remote.RemoteDataSourceApi

interface NetworkProvider {
    fun provideRemoteDataSourceApi(): RemoteDataSourceApi
}