package com.example.cakemvvmapp.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cakemvvmapp.network.ClientInterface

class CakeModelViewFactory(private val clientInterface: ClientInterface
                           , private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CakeModelViewModel(clientInterface, application) as T
    }
}