package com.example.propertieshostelworld.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.propertieshostelworld.model.City
import com.example.propertieshostelworld.model.Image
import com.example.propertieshostelworld.model.Location
import com.example.propertieshostelworld.model.Price
import com.example.propertieshostelworld.model.Property
import com.example.propertieshostelworld.model.PropertyResults
import com.example.propertieshostelworld.model.Rating
import com.example.propertieshostelworld.repository.PropertyRepository
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

    private lateinit var viewModel: ListViewModel
    private lateinit var repository: PropertyRepository

    @Before
    fun setUp() {
        repository = Mockito.mock(PropertyRepository::class.java)
        viewModel = ListViewModel(repository)
    }

    @Test
    fun callApi_onNext() {
        val properties = ArrayList<Property>()
        properties.add(Property("Hostel X", Rating(80), "Nice", Price(8.5), listOf(Image("", ""))))

        val location = Location(City("Dublin", "Ireland"))

        Mockito.`when`(repository.getProperties(any())).then {
            it.getArgument<PropertyRepository.ApiObserver>(0).onNext(Response.success(PropertyResults(properties, location)))
        }

        viewModel.callApi()

        assertEquals(properties, viewModel.properties.value)
        assertEquals(location, viewModel.location.value)
    }


    @Test
    fun callApi_onError() {
        Mockito.`when`(repository.getProperties(any())).then {
            it.getArgument<PropertyRepository.ApiObserver>(0).onError(Throwable())
        }

        viewModel.callApi()

        assertEquals(ArrayList<Property>(), viewModel.properties.value)
        assertEquals(Location(City("","")), viewModel.location.value)
    }

    private fun <T> any(): T {
        return org.mockito.ArgumentMatchers.any()
    }
}
