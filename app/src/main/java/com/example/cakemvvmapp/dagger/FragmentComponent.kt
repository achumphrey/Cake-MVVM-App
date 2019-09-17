package com.example.cakemvvmapp.dagger

import com.example.cakemvvmapp.view.CakeModelFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(FragmentModule::class, NetworkModule::class))
interface FragmentComponent {
    fun inject(cakeModelFragment: CakeModelFragment)
}