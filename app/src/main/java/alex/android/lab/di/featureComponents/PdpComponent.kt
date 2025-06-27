package alex.android.lab.di.featureComponents

import alex.android.lab.di.AppComponent
import alex.android.lab.di.modules.presentationModules.PdpModule
import alex.android.lab.di.scopes.FeatureScope
import alex.android.lab.di.viewModelModules.ViewModelModule
import alex.android.lab.presentation.view.PdpFragment
import dagger.Component


@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [PdpModule::class, ViewModelModule::class]
)
interface PdpComponent {
    fun inject(fragment: PdpFragment)
}