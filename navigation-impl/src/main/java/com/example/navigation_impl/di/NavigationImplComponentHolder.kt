package com.example.navigation_impl.di

import com.example.di.BaseComponentHolder

object NavigationImplComponentHolder : BaseComponentHolder<
        NavigationImplApi,
        NavigationImplDependencies,
        >() {

    override fun build(dependencies: NavigationImplDependencies): NavigationImplApi =
        DaggerNavigationImplComponent.factory().create(dependencies)
}