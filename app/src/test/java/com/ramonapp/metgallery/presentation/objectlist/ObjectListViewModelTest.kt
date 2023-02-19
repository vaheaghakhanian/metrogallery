package com.ramonapp.metgallery.presentation.objectlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.repository.ObjectRepository
import com.ramonapp.metgallery.domain.usecase.SearchIDsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class ObjectListViewModelTest {

    private lateinit var viewModel: ObjectListViewModel
    private lateinit var searchIDsUseCase: SearchIDsUseCase
    private lateinit var repository: ObjectRepository

    private val testDispatcher = StandardTestDispatcher()


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = mock(ObjectRepository::class.java)
        searchIDsUseCase = SearchIDsUseCase(repository)
        viewModel = ObjectListViewModel(searchIDsUseCase, testDispatcher)
    }

    @Test
    fun test_fetch_empty_ids_fill_the_object_flow_empty() {
        runTest {
            `when`(repository.searchIDs("persia")).thenReturn(DataResult.Success(emptyList()))
            viewModel.fetchObjects()
            assertThat(viewModel.showLoading.value).isFalse()
            assertThat(viewModel.objectList.first().isEmpty()).isTrue()
        }
    }

}