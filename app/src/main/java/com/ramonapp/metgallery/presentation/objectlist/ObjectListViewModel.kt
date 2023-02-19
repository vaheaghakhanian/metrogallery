package com.ramonapp.metgallery.presentation.objectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.usecase.SearchIDsUseCase
import com.ramonapp.metgallery.util.ErrorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ObjectListViewModel @Inject constructor(
    private val searchIDsUseCase: SearchIDsUseCase,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "persia"
    }

    private var _objectList = MutableStateFlow<List<Int>>(emptyList())
    val objectList: StateFlow<List<Int>> = _objectList
    private var _showError = MutableSharedFlow<Int>()
    val showError: SharedFlow<Int> = _showError
    private var _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    fun fetchObjects(query: String? = null) {
        viewModelScope.launch(dispatcherIO) {
            _showLoading.value = true
            when(val result = searchIDsUseCase(query ?: DEFAULT_QUERY)) {
                is DataResult.Success -> {
                    _objectList.value = result.data!!
                }
                is DataResult.Failure -> {
                    _showError.emit(ErrorMapper.map(result.error))
                }
            }
            _showLoading.value = false
        }
    }


}