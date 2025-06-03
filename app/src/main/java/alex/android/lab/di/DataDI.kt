package alex.android.lab.di

import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.data.DataSource.LocalDataSource.MainRoomDB
import alex.android.lab.data.DataSource.LocalDataSource.mappers.EntityMapper
import alex.android.lab.data.DataSource.RemoteDataSource.RemoteDataSource
import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.ProductsApiService
import alex.android.lab.data.DataSource.RemoteDataSource.RetrofitClient.RetrofitClient
import alex.android.lab.data.mappers.ProductListMapperDTO
import alex.android.lab.data.repositorieslmpl.CartRepositoryImpl
import alex.android.lab.data.repositorieslmpl.ProductsRepositoryImpl
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {
    single<ProductsRepository> {
        ProductsRepositoryImpl(
            remoteDataSource = get(),
            productListMapper = get(),
            entityMapper = get(),
            dao = get()
        )
    }
    single<CartRepository> {
        CartRepositoryImpl(dao = get())
    }
    single<RemoteDataSource> {
        RemoteDataSource(api = get())
    }
    single<ProductsApiService> {
        RetrofitClient.productsApiService
    }
    single<MainRoomDB> {
        MainRoomDB.getDB(androidContext())
    }
    single<Dao> {
        get<MainRoomDB>().getDao()
    }
    single <EntityMapper> {
        EntityMapper()
    }
    single <ProductListMapperDTO> {
        ProductListMapperDTO()
    }
}