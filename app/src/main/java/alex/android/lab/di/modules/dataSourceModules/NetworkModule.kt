package alex.android.lab.di.modules.dataSourceModules

import alex.android.lab.data.DataSource.RemoteDataSource.RemoteDataSource
import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.ProductsApiService
import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.RetrofitClient
import alex.android.lab.di.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    @AppScope
    fun provideProductsApiService(): ProductsApiService = RetrofitClient.productsApiService

    @Provides
    @AppScope
    fun provideRemoteDataSource(api: ProductsApiService): RemoteDataSource = RemoteDataSource(api)
}
