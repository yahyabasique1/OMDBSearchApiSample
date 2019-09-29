package com.example.omdbsearchapisample.di.component

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.omdbsearchapisample.ApplicationClass
import com.example.omdbsearchapisample.di.ApplicationContext
import com.example.omdbsearchapisample.di.DatabaseInfo
import com.example.omdbsearchapisample.di.module.ApplicationModule
import com.example.omdbsearchapisample.di.module.DataBaseModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import com.example.omdbsearchapisample.MainActivity
import com.example.omdbsearchapisample.di.module.ActivityModule
import com.example.omdbsearchapisample.di.module.ApiInterfaceModule
import com.example.omdbsearchapisample.fragment.MovieListFragment
import com.example.omdbsearchapisample.networking.ApiInterface
import dagger.BindsInstance
import dagger.android.AndroidInjectionModule


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        DataBaseModule::class,
        ActivityModule::class,
        ApiInterfaceModule::class
    ]
)
interface ApplicationComponent {


    fun inject(testApplication: ApplicationClass)


    @ApplicationContext
    fun getContext(): Context

    fun application(): Application

    fun apiInterface(): ApiInterface


}