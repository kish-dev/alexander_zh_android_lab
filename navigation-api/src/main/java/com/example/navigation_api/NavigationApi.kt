package com.example.navigation_api

interface NavigationApi <DIRECTION> {

    fun navigate(direction: DIRECTION)
}