package com.example.cakemvvmapp.view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cakemvvmapp.database.CakeDatabase
import com.example.cakemvvmapp.model.CakeModel
import com.example.cakemvvmapp.network.ClientInterface
import com.example.cakemvvmapp.network.RetrofitInstance
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class CakeModelViewModel (application: Application) : AndroidViewModel(application) {

    private var cakeList: MutableLiveData<List<CakeModel>>? = MutableLiveData()
     var cakeListFromDb: MutableLiveData<List<CakeModel>>? = MutableLiveData()
    private var showProgress: MutableLiveData<Boolean>? = MutableLiveData()
    var compositeDisposable = CompositeDisposable()
    lateinit var disposable: Disposable
    private var showDBSuccess: MutableLiveData<Boolean>? = MutableLiveData()
    var cakeDao = CakeDatabase.getDatabase(application)?.cakeDao()

    fun getShowProgress():MutableLiveData<Boolean>?{
        return showProgress
    }

    fun getShowDBSuccess(): MutableLiveData<Boolean>?{
        return showDBSuccess
    }

   private fun addCakeToDatabase(cakeModel: List<CakeModel>){
        compositeDisposable.add(
            cakeDao!!.insertUser(cakeModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({showDBSuccess?.value = true},{
                    Log.i("ViewModel error",it.message)
                    showDBSuccess?.value=false})
        )
    }

    fun getAllCakesFromDb(){
        compositeDisposable.add(
            cakeDao!!.getAllCakes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({cakes -> cakeListFromDb?.value = cakes
                    showDBSuccess?.value = true })
        )
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
        addCakeToDatabase(listCake)
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
