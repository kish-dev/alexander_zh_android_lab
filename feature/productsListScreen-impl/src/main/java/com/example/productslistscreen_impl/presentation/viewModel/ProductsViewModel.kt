package com.example.productslistscreen_impl.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productScreen_impl.data.ProductListMapperDTO
import com.example.productslistscreen_impl.domain.interactor.CartInteractor
import com.example.productslistscreen_impl.domain.interactor.ProductsInteractor
import com.example.productslistscreen_impl.domain.models.UIStates
import com.example.productslistscreen_impl.presentation.mappers.ProductListMapper
import com.example.productslistscreen_impl.presentation.models.ProductInListVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsInteractor: ProductsInteractor,
    private val cartInteractor: CartInteractor,
    private val productListMapper: ProductListMapper
) : ViewModel() {

    private val _productsLD = MutableLiveData<UIStates<List<ProductInListVo>>>()
    val productsLD: LiveData<UIStates<List<ProductInListVo>>> = _productsLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String> = _errorLD

    private val _inCartProductsCountLD = MutableLiveData<Int>()
    val inCartProductsCountLD: LiveData<Int> = _inCartProductsCountLD

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _productsLD.postValue(UIStates.Loading())
            while (true) {
                delay(5000)
                _productsLD.postValue(productListMapper.mapUiStateDTOtoVO(productsInteractor.getProducts()))
            }
        }
    }

    fun getInCartProductsCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _inCartProductsCountLD.postValue(cartInteractor.getInCartProductsCount())
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