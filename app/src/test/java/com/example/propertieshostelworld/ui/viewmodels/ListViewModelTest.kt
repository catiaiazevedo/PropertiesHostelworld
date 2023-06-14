package com.example.propertieshostelworld.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.propertieshostelworld.model.Property
import com.example.propertieshostelworld.model.PropertyResults
import com.example.propertieshostelworld.repository.PropertyRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import retrofit2.Response
import kotlin.collections.ArrayList

class ListViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: PropertyRepository
    private lateinit var viewModel: ListViewModel

    @Before
    fun setUp() {
        repository = Mockito.mock(PropertyRepository::class.java)
        viewModel = ListViewModel(repository)
    }

    @Before
    fun setupSchedulers() {
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    //I changed ListViewModel implementation to allow testing and everything seemed to be correct unless the
    //.observeOn(AndroidSchedulers.mainThread()), that even with Schedulers.trampoline() continues to give
    //java.lang.NullPointerException (.subscribeOn(Schedulers.io()) passes)
    //My goal was to test all the methods of the interface, being them onNext, onSubscribe, onComplete, onError
    //but for all of them callApi needs to be called where Schedulers for io and ui need to be provided and the same
    //problem occurs
    @Test
    fun callApi_onError() {
        val observer = viewModel.observer
        val response = object: Observable<Response<PropertyResults>>() {
            override fun subscribeActual(observer: Observer<in Response<PropertyResults>>?) {}
        }

        Mockito.`when`(repository.getAllProperties()).thenReturn(response)

        Mockito.`when`(viewModel.setResponse(observer, response)).then {
            observer.onError(Throwable())
        }

        viewModel.callAll()

        assertEquals(ArrayList<Property>(), viewModel.properties.value)
    }
}
