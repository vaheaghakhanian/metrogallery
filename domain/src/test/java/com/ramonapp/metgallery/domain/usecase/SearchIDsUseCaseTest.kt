package com.ramonapp.metgallery.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.repository.ObjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class SearchIDsUseCaseTest {

    private lateinit var searchIDsUseCase: SearchIDsUseCase
    private lateinit var repository: ObjectRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = mock(ObjectRepository::class.java)
        searchIDsUseCase = SearchIDsUseCase(repository)
    }

    @Test
    fun test_fetch_empty_ids_return_success() {
        runTest {
            `when`(repository.searchIDs("persia")).thenReturn(DataResult.Success(emptyList()))
            assertThat(searchIDsUseCase("persia")).isInstanceOf(DataResult.Success::class.java)
        }
    }


    @Test
    fun test_fetch_ids_return_list() {
        runTest {
            `when`(repository.searchIDs("persia")).thenReturn(
                DataResult.Success(
                    listOf(1, 2, 3, 4, 5)
                )
            )
            assertThat(searchIDsUseCase("persia")).isEqualTo(DataResult.Success(listOf(1,2,3,4,5)))
        }
    }
}