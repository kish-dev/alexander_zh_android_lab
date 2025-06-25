package alex.android.lab.di.modules

import alex.android.lab.data.DataSource.LocalDataSource.mappers.EntityMapper
import alex.android.lab.data.mappers.ProductListMapperDTO
import alex.android.lab.di.scopes.AppScope
import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.presentation.mappers.ProductListMapper
import dagger.Binds
import dagger.Module
import dagger.Provides

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