package com.example.cakemvvmapp.view

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cakemvvmapp.R
import com.example.cakemvvmapp.model.CakeModel
import com.example.cakemvvmapp.network.ClientInterface
import com.example.cakemvvmapp.network.RetrofitInstance
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.cake_model_fragment.*


class CakeModelViewModel : ViewModel() {

    private var cakeList: MutableLiveData<List<CakeModel>>? = MutableLiveData()
    private var showProgress: MutableLiveData<Boolean>? = MutableLiveData()
    var compositeDisposable = CompositeDisposable()
    lateinit var disposable: Disposable

    fun getShowProgress():MutableLiveData<Boolean>?{
        return showProgress
    }

    fun processCall() {

        showProgress?.value = true

        val cakeModelInterface =
            RetrofitInstance().retrofitInstance.create(ClientInterface::class.java)

        val cakeModelObservable: Observable<List<CakeModel>>
                = cakeModelInterface.getCakeRecords()

        cakeModelObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        //    .subscribe(cakeObserver())
            .subscribe({t-> makeCakeList(t)
                showProgress?.value = false})
    }


    fun onShowList() : MutableLiveData<List<CakeModel>>?{
        return cakeList
    }

    private fun makeCakeList(listCake: List<CakeModel>) {
        Log.i(TAG, "${listCake[1].title}")
        cakeList?.value = listCake
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel destroyed")
    }

    private fun cakeObserver(): Observer<List<CakeModel>> {

        return object : Observer<List<CakeModel>> {

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: List<CakeModel>) {
                Log.i(TAG, "${t[0].title}")
                showProgress?.value = false

                makeCakeList(t)
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, "something went wrong")
            }
        }
    }

    fun showError(){
        Log.i("SHOW_ERROR", "Something Happened")
    }

    fun onDestroy() {
        disposable.dispose()
        compositeDisposable.clear()
    }

    companion object{
        const val TAG = "CAKEVIEWMODEL"
    }

}
