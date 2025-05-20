package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.UiStates.UIStates
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.mappers.ProductListMapper
import alex.android.lab.presentation.viewObject.ProductInListVO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productsInteractor: ProductsInteractor,
    private val productListMapper: ProductListMapper
) : ViewModel() {

    private val _productsLD = MutableLiveData<UIStates<List<ProductInListVO>>>()
    val productsLD: LiveData<UIStates<List<ProductInListVO>>> = _productsLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String> = _errorLD

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _productsLD.postValue(UIStates.Loading())
           // _productsLD.postValue(ProductListMapper.mapUiStateDTOtoVO(interactor.getProducts()))
            while (true) {
                delay(5000)
                _productsLD.postValue(productListMapper.mapUiStateDTOtoVO(productsInteractor.getProducts()))
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _productsLD.postValue(productListMapper.mapUiStateDTOtoVO(productsInteractor.getProducts()))
        }
    }

    fun loadProductsDB() {
        viewModelScope.launch(Dispatchers.IO) {
            _productsLD.postValue(productListMapper.mapUiStateDTOtoVO(productsInteractor.getProductsDB()))
        }
    }

    fun toggleFavorite(productId: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            productsInteractor.toggleFavorite(productId, isFavorite)
            _productsLD.postValue(productListMapper.mapUiStateDTOtoVO(productsInteractor.getProducts()))
        }
    }

    fun setError(error: String) {
        _errorLD.value = error
    }
}