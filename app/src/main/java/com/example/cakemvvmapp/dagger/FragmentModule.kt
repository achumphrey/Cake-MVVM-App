package com.example.cakemvvmapp.dagger

import android.app.Application
import com.example.cakemvvmapp.network.ClientInterface
import com.example.cakemvvmapp.view.CakeModelViewFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentModule {

    @Provides
    @Singleton
    fun provideCakeViewModelFactory(clientInterface: ClientInterface,
                                    application: Application): CakeModelViewFactory{
        return CakeModelViewFactory(clientInterface, application)
    }
}