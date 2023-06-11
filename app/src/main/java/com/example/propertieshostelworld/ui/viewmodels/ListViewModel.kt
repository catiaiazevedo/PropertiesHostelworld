package com.example.propertieshostelworld.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.propertieshostelworld.model.Location
import com.example.propertieshostelworld.model.Property
import com.example.propertieshostelworld.model.PropertyResults
import com.example.propertieshostelworld.network.RetrofitClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListViewModel: ViewModel() {
    private val TAG = "ListViewModel"

    private var client: RetrofitClient = RetrofitClient()

    var properties: MutableLiveData<List<Property>> = MutableLiveData()
    var location: MutableLiveData<Location> = MutableLiveData()
    lateinit var disposable: Disposable

    fun callApi() {
        val response = client.getService().getAllProperties()
        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getPropertyResultsObserver())
    }

    private fun getPropertyResultsObserver(): Observer<PropertyResults> {
        return object : Observer<PropertyResults> {

            override fun onNext(property: PropertyResults) {
                properties.postValue(property.properties)
                location.postValue(property.location)
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onComplete() {
                Log.d(TAG, "COMPLETED")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, e.message.toString())
            }
        }
    }
}
