package com.example.cakemvvmapp.dagger

import android.app.Application
import com.example.cakemvvmapp.common.Constants
import com.example.cakemvvmapp.network.ClientInterface
import com.example.cakemvvmapp.view.CakeModelViewFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val application: MyApplication) {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideClientInterface(retrofit: Retrofit)
            = retrofit.create(ClientInterface::class.java)

    @Provides
    @Singleton
    fun provideApplicationContext(): Application = application

    @Provides
    @Singleton
    fun provideCakeViewModelFactory(clientInterface: ClientInterface, application: Application): CakeModelViewFactory{
       return CakeModelViewFactory(clientInterface, application)
    }
}