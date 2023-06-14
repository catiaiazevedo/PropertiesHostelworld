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
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

//repository consumes PropertyRepository(ApiService) and ListViewModel(PropertyRepository) provides for binds
@HiltViewModel
class ListViewModel @Inject constructor(private val repository: PropertyRepository) : ViewModel() {
    private val TAG = "ListViewModel"

    var properties: MutableLiveData<List<Property>> = MutableLiveData()
    var location: MutableLiveData<Location> = MutableLiveData()
    lateinit var disposable: Disposable

    val observer = object : ApiObserver {
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
    }

    fun callAll() {
        callApi(observer)
    }

    private fun callApi(apiObserver: ApiObserver) {
        repository.getAllProperties().let { response ->
            setResponse(apiObserver, response)
        }
    }

    fun setResponse(apiObserver: ApiObserver, response: Observable<Response<PropertyResults>>) {
        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<PropertyResults>> {
                override fun onNext(response: Response<PropertyResults>) {
                    apiObserver.onNext(response)
                }

                override fun onSubscribe(d: Disposable) {
                    apiObserver.onSubscribe(d)
                }

                override fun onComplete() {
                    apiObserver.onComplete()
                }

                override fun onError(e: Throwable) {
                    apiObserver.onError(e)
                }
            })
    }

    interface ApiObserver {
        fun onNext(response: Response<PropertyResults>)

        fun onSubscribe(d: Disposable)

        fun onComplete()

        fun onError(e: Throwable)
    }
}
