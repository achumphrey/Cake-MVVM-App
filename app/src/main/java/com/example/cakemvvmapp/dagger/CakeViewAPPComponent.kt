package com.example.cakemvvmapp.dagger

import com.example.cakemvvmapp.view.CakeModelFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface CakeViewAPPComponent {

    fun inject(myApplication: MyApplication)
    fun inject(cakeModelFragment: CakeModelFragment)
}