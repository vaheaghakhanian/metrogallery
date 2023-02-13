package com.ramonapp.pixabay.presentation.imagelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.ramonapp.pixabay.FakeRepository
import com.ramonapp.pixabay.MainCoroutineRule
import com.ramonapp.pixabay.domain.usecase.FetchImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageListViewModelTest {

    private lateinit var viewModel: ImageListViewModel
    private lateinit var fetchImagesUseCase: FetchImagesUseCase
    private lateinit var repository: FakeRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = FakeRepository()
        fetchImagesUseCase = FetchImagesUseCase(repository)
        viewModel = ImageListViewModel(fetchImagesUseCase)
    }

    @Test
    fun test_fetch_images_fill_the_image_list() {
        runTest {
            Dispatchers.setMain(StandardTestDispatcher())
            viewModel.fetchImages()
            advanceUntilIdle()
            assertThat(viewModel.showLoading.value).isFalse()
            assertThat(viewModel.imageList.first().isNotEmpty()).isTrue()
        }
    }

}