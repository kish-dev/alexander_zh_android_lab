package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.exception.DomainException
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PdpViewModel(
    private val interactor: ProductsInteractor
) : ViewModel() {
    private val _productLD = MutableLiveData<ProductInListVO>()
    val productLD: LiveData<ProductInListVO> = _productLD

    fun loadProduct(guid: String) {
        try {
            _productLD.value = interactor.getProductById(guid)
        }catch (e: DomainException) {
            Log.e("error", "Exception caught: ${e.message}")
        }

    }
}