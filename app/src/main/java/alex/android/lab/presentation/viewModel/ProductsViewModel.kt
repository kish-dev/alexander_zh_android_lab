package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val interactor: ProductsInteractor,
) : ViewModel() {

    private val _productsLD = MutableLiveData<ApiResult<List<ProductInListVO>>>()
    val productsLD: LiveData<ApiResult<List<ProductInListVO>>> = _productsLD

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(5000)
                _productsLD.postValue(interactor.getProducts())
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _productsLD.postValue(interactor.getProductsDB())
        }
    }

    fun setInitialData(products: List<ProductInListVO>) {
        _productsLD.value = ApiResult.Success(products)
    }

    fun toggleFavorite(productId: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.toggleFavorite(productId, isFavorite)
            _productsLD.postValue(interactor.getProducts())
        }
    }

}