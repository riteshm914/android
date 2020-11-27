package com.demo.mvvm.app

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.demo.mvvm.app.data.model.FactModel
import com.demo.mvvm.app.data.model.Rows
import com.demo.mvvm.app.ui.main.MainActivity
import com.demo.mvvm.app.ui.main.MainViewModel
import com.demo.mvvm.app.utils.Resource
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private val factListLiveData = MutableLiveData<Resource<FactModel>>()

    @Before
    @Throws(Exception::class)
    fun setUp() {

        // Mock our View Model to stub out calls later
        viewModel = Mockito.mock(MainViewModel::class.java)

        Mockito.doReturn(factListLiveData)
            .`when`(viewModel).facts
    }

    @Test
    fun responseSuccess_displayRecyclerview() {

        // Given
        factListLiveData.postValue(Resource.success(getDummyFacts()))

        countingTaskExecutorRule.drainTasks(2, TimeUnit.SECONDS)

        // Then
        onView(withId(R.id.fact_list)).check(matches(isDisplayed()))

    }

    @Test
    fun responseFailure_hideRecyclerview() {

        //  Given
        factListLiveData.postValue(Resource.error("no data found", null))

        countingTaskExecutorRule.drainTasks(2, TimeUnit.SECONDS)

        // Then
        onView(withId(R.id.fact_list)).check(matches(not(isDisplayed())))
    }

    private fun getDummyFacts(): FactModel {
        val rowList = ArrayList<Rows>()

        val row = Rows(
            "title",
            "description",
            ""
        )
        rowList.add(row)
        return FactModel(
            "Root Title", rowList
        )
    }
}