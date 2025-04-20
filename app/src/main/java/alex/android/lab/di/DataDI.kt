package alex.android.lab.di

import alex.android.lab.data.repositorieslmpl.MockProductsRepositoryImpl
import alex.android.lab.domain.repositories.ProductsRepository
import org.koin.dsl.module


val dataModule = module {
    single<ProductsRepository> {
        MockProductsRepositoryImpl()
    }
}
