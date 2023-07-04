package com.example.propertieshostelworld.repository

import com.example.propertieshostelworld.model.PropertyResults
import com.example.propertieshostelworld.network.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

//api consumes provideRetrofitInstance(String) and PropertyRepository(ApiService) provides for ListViewModel
class PropertyRepository @Inject constructor(private val api: ApiService) {
    fun getProperties(observer: ApiObserver) {
        api.getAllProperties().let { response ->
            response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<PropertyResults>> {
                    override fun onNext(response: Response<PropertyResults>) = observer.onNext(response)

                    override fun onSubscribe(d: Disposable) = observer.onSubscribe(d)

                    override fun onComplete() = observer.onComplete()

                    override fun onError(e: Throwable) = observer.onError(e)
                })
        }
    }

    interface ApiObserver {
        fun onNext(response: Response<PropertyResults>)

        fun onSubscribe(d: Disposable)

        fun onComplete()

        fun onError(e: Throwable)
    }
}
