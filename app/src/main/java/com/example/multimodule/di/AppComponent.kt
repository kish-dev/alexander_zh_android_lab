package com.example.multimodule.di

import com.example.database_impl.di.DatabaseComponent
import com.example.network_impl.di.NetworkComponent
import com.example.multimodule.MainApplication
import com.example.navigation_impl.NavigationActivityProvider
import com.example.database_impl.di.DatabaseModule
import com.example.network_impl.di.NetworkModule
import com.example.productslistscreen_impl.di.ProductsScreenDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [AppModule::class]
)
internal interface AppComponent {

    fun inject(app: MainApplication)

    val productsScreenDependencies: ProductsScreenDependencies

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activityProvider: NavigationActivityProvider,
        ): AppComponent
    }
}