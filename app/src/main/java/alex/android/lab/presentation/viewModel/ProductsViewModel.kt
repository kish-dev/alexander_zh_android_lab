package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsViewModel(private val interactor: ProductsInteractor) : ViewModel() {
    private val _productsLD = MutableLiveData<List<ProductInListVO>>()
    val productsLD: LiveData<List<ProductInListVO>> = _productsLD

    init {
        _productsLD.value = interactor.getProducts()
    }
}