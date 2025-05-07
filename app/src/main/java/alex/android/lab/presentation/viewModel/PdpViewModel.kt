package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PdpViewModel(
    private val interactor: ProductsInteractor
) : ViewModel() {
    private val _productLD = MutableLiveData<ProductInListVO>()
    val productLD: LiveData<ProductInListVO> = _productLD

    fun loadProduct(guid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.incrementViewCount(guid)
            _productLD.postValue(interactor.getProductById(guid))
        }
    }

    fun toggleFavorite(productId: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.toggleFavorite(productId, isFavorite)
            loadProduct(productId)
        }
    }
}