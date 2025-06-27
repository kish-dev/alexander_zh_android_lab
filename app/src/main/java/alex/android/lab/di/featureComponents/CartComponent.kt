import alex.android.lab.di.AppComponent
import alex.android.lab.di.modules.presentationModules.CartModule
import alex.android.lab.di.scopes.FeatureScope
import alex.android.lab.di.viewModelModules.ViewModelModule
import alex.android.lab.presentation.view.CartFragment
import dagger.Component


@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CartModule::class, ViewModelModule::class]
)
interface CartComponent {
    fun inject(fragment: CartFragment)
}
