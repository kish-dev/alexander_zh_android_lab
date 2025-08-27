package com.example.navigation_impl.di

import com.example.di.BaseDependencies
import com.example.navigation_impl.NavigationActivityProvider

interface NavigationImplDependencies : BaseDependencies {

    val activityProvider: NavigationActivityProvider
}