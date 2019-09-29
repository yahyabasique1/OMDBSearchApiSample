package com.example.omdbsearchapisample.di.module

import com.example.omdbsearchapisample.networking.ApiInterface
import com.example.omdbsearchapisample.networking.BASE_URL
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder



@Module(includes = [OkHttpModule::class])
class ApiInterfaceModule {

    @Provides
    fun apiInterface(retrofit: Retrofit):ApiInterface{
        return retrofit.create(ApiInterface::class.java)

    }

    @Provides
    fun retrofitClient(okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory,rxJava2CallAdapterFactory: RxJava2CallAdapterFactory):Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()


    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }


    @Provides
    fun gsonConverter(gson: Gson):GsonConverterFactory{
        return GsonConverterFactory.create(gson)
    }


    @Provides
    fun rxjavaAdapter():RxJava2CallAdapterFactory{
        return RxJava2CallAdapterFactory.create()
    }
}