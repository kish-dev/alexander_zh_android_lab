package alex.android.lab.di

import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.data.DataSource.LocalDataSource.MainRoomDB
import alex.android.lab.data.DataSource.LocalDataSource.mappers.EntityMapper
import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import alex.android.lab.presentation.mappers.ProductListMapper
import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.ProductsApiService
import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.RetrofitClient
import alex.android.lab.data.DataSource.RemoteDataSource.RemoteDataSource
import alex.android.lab.data.mappers.ProductListMapperDTO
import alex.android.lab.data.repositorieslmpl.CartRepositoryImpl
import alex.android.lab.data.repositorieslmpl.ProductsRepositoryImpl
import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@AppScope
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        MapperModule::class
    ]
)
interface AppComponent {
    fun productsRepository(): ProductsRepository
    fun cartRepository(): CartRepository
    fun productListMapper(): ProductListMapper
    fun uiStatesMapper(): UIStatesMapper
}

@Module
class AppModule(private val application: Application) {
    @Provides
    @AppScope
    fun provideApplicationContext(): Context = application
}

@Module
class NetworkModule {
    @Provides
    @AppScope
    fun provideProductsApiService(): ProductsApiService = RetrofitClient.productsApiService

    @Provides
    @AppScope
    fun provideRemoteDataSource(api: ProductsApiService): RemoteDataSource = RemoteDataSource(api)
}

@Module
class DatabaseModule {
    @Provides
    @AppScope
    fun provideDatabase(context: Context): MainRoomDB = MainRoomDB.getDB(context)

    @Provides
    @AppScope
    fun provideDao(database: MainRoomDB): Dao = database.getDao()
}

@Module
class RepositoryModule {
    @Provides
    @AppScope
    fun provideProductsRepository(
        remoteDataSource: RemoteDataSource,
        productListMapper: ProductListMapperDTO,
        entityMapper: EntityMapper,
        dao: Dao
    ): ProductsRepository = ProductsRepositoryImpl(remoteDataSource, productListMapper, entityMapper, dao)

    @Provides
    @AppScope
    fun provideCartRepository(dao: Dao): CartRepository = CartRepositoryImpl(dao)
}

@Module
class MapperModule {
    @Provides
    @AppScope
    fun provideProductListMapper(): ProductListMapper = ProductListMapper()

    @Provides
    @AppScope
    fun provideProductListMapperDTO(): ProductListMapperDTO = ProductListMapperDTO()

    @Provides
    @AppScope
    fun provideEntityMapper(): EntityMapper = EntityMapper()

    @Provides
    @AppScope
    fun provideUIStatesMapper(): UIStatesMapper = UIStatesMapper()
}