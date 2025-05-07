package alex.android.lab.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ErrorPageViewModel(

): ViewModel() {

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String> = _errorLD

    fun setError(error: String) {
        _errorLD.value = error
    }
}