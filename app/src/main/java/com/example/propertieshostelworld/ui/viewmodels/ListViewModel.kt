package com.example.propertieshostelworld.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.propertieshostelworld.model.City
import com.example.propertieshostelworld.model.Location
import com.example.propertieshostelworld.model.Property
import com.example.propertieshostelworld.model.PropertyResults
import com.example.propertieshostelworld.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import retrofit2.Response
import javax.inject.Inject

//repository consumes PropertyRepository(ApiService) and ListViewModel(PropertyRepository) provides for binds
@HiltViewModel
class ListViewModel @Inject constructor(private val repository: PropertyRepository) : ViewModel() {
    private val TAG = "ListViewModel"

    var properties: MutableLiveData<List<Property>> = MutableLiveData()
    var location: MutableLiveData<Location> = MutableLiveData()
    lateinit var disposable: Disposable

    fun callApi() {
        repository.getProperties(object : PropertyRepository.ApiObserver {
            override fun onNext(response: Response<PropertyResults>) {
                properties.postValue(response.body()?.properties)
                location.postValue(response.body()?.location)
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onComplete() {
                Log.d(TAG, "COMPLETED")
            }

            override fun onError(e: Throwable) {
                properties.value = ArrayList()
                location.value = Location(City("", ""))
            }
        })
    }
}
