package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingViewModel(
    private val interactor: ProductsInteractor
): ViewModel() {

    private val _productsLD = MutableLiveData<ApiResult<List<ProductInListVO>>>()
    val productsLD: LiveData<ApiResult<List<ProductInListVO>>> = _productsLD

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tryLoadProducts()
        }
    }
    suspend fun tryLoadProducts() {
        val products = interactor.getProducts()
        _productsLD.postValue(products)
    }
}