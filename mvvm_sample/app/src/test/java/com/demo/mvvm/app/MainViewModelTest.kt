package com.demo.mvvm.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.mvvm.app.data.model.FactModel
import com.demo.mvvm.app.data.repository.MainRepository
import com.demo.mvvm.app.ui.main.MainViewModel
import com.demo.mvvm.app.utils.Resource
import com.demo.mvvm.app.utils.TestCoroutineRule
import com.demo.mvvm.app.utils.TestUtil

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var mainRepository: MainRepository


    @Mock
    private lateinit var observer: Observer<Resource<FactModel>>

    private lateinit var viewModel: MainViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {

    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {


        testCoroutineRule.runBlockingTest {
            doReturn(TestUtil.facts)
                .`when`(mainRepository)
                .getFacts()

            viewModel = MainViewModel(
                mainRepository
                )
            viewModel.facts.observeForever(observer)
            viewModel.fetchFacts()
            verify(mainRepository, atLeastOnce()).getFacts()
            verify(observer).onChanged(Resource.success(TestUtil.facts))
            viewModel.facts.removeObserver(observer)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(mainRepository)
                .getFacts()
            val viewModel = MainViewModel(
                mainRepository
            )
            viewModel.facts.observeForever(observer)
            viewModel.fetchFacts()
            verify(mainRepository).getFacts()
            verify(observer).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.facts.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }
}