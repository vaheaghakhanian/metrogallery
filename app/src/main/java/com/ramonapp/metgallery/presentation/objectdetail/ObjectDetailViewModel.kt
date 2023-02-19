package com.ramonapp.metgallery.presentation.objectdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.model.ErrorType
import com.ramonapp.metgallery.domain.model.ObjectModel
import com.ramonapp.metgallery.domain.usecase.GetObjectUseCase
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
internal class ObjectDetailViewModel @Inject constructor(
    private val getObjectUseCase: GetObjectUseCase,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private var _obj = MutableStateFlow<ObjectModel?>(null)
    val obj: StateFlow<ObjectModel?> = _obj
    private var _showError = MutableSharedFlow<Int>()
    val showError: SharedFlow<Int> = _showError
    private var _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    fun getObject(id: Int?) {
        viewModelScope.launch(dispatcherIO) {
            if (id == null) {
                _showError.emit(ErrorMapper.map(ErrorType.UnExpectedError))
                return@launch
            }
            _showLoading.value = true
            when (val result = getObjectUseCase(id)) {
                is DataResult.Success -> {
                    _obj.value = result.data!!
                }
                is DataResult.Failure -> {
                    _showError.emit(ErrorMapper.map(result.error))
                }
            }
            _showLoading.value = false
        }
    }


}