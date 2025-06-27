import alex.android.lab.di.AppComponent
import alex.android.lab.di.modules.presentationModules.ProductsModule
import alex.android.lab.di.scopes.FeatureScope
import alex.android.lab.di.viewModelModules.ViewModelModule
import alex.android.lab.presentation.view.ProductsFragment
import dagger.Component


@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ProductsModule::class, ViewModelModule::class]
)
interface ProductsComponent {
    fun inject(fragment: ProductsFragment)
}