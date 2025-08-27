package com.example.database_api

interface DatabaseProvider {
    fun provideDatabaseApi(): DatabaseApi
}