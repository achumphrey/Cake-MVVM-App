package com.example.cakemvvmapp.dagger

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerCakeViewAPPComponent.builder()
            .networkModule(NetworkModule(this))
            .build()
            .inject(this)
    }

}