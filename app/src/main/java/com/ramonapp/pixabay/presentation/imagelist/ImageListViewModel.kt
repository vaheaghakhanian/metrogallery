package com.ramonapp.pixabay.presentation.imagelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageModel
import com.ramonapp.pixabay.domain.usecase.FetchImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ImageListViewModel @Inject constructor(
    private val fetchImagesUseCase: FetchImagesUseCase
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "fruits"
    }

    private var _imageList = MutableStateFlow<List<ImageModel>>(emptyList())
    val imageList: StateFlow<List<ImageModel>> = _imageList
    private var _showError = MutableSharedFlow<String>()
    val showError: SharedFlow<String> = _showError
    private var _showLoading = MutableStateFlow<Boolean>(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    init {
        fetchImages()
    }

    fun fetchImages(query: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.value = true
            when(val result = fetchImagesUseCase(query ?: DEFAULT_QUERY)) {
                is DataResult.Success -> {
                    _imageList.value = result.data!!
                }
                is DataResult.Failure -> {
                    _showError.emit(result.e.message ?: "GENERAL ERROR")
                }
            }
            _showLoading.value = false
        }
    }


}