package com.example.cakemvvmapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cakemvvmapp.model.CakeModel
import com.example.cakemvvmapp.network.ClientInterface
import com.example.cakemvvmapp.network.RetrofitInstance
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CakeViewModel: ViewModel() {

    var compositeDisposable = CompositeDisposable()
    lateinit var disposable: Disposable

    fun processCall() {

        val cakeModelInterface =
            RetrofitInstance().retrofitInstance.create(ClientInterface::class.java)

        val cakeModelObservable: Observable<List<CakeModel>>
                = cakeModelInterface.getCakeRecords()

        cakeModelObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //     .subscribe(starWarsObserver())
            .subscribe({t-> getCakeList(t)}, {showError()})
       //     .subscribe(numbersObsever)
    }

    private fun cakeObserver(): Observer<List<CakeModel>> {

        return object : Observer<List<CakeModel>> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d

            }

            override fun onNext(t: List<CakeModel>) {
                getCakeList(t)
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, "something went wrong")
            }
        }
    }

    fun getCakeList(t: List<CakeModel>): List<CakeModel>{
        var cakeList: List<CakeModel> = t
        return cakeList
    }

    fun showError(){
    }

     fun onDestroy() {
        disposable.dispose()
        compositeDisposable.clear()
    }

    companion object{
        const val TAG = "CAKEVIEWMODEL"
    }

}