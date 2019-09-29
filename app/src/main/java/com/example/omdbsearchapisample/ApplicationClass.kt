package com.example.omdbsearchapisample

import android.app.Activity
import android.app.Application
import com.example.omdbsearchapisample.di.module.ApplicationModule
import com.example.omdbsearchapisample.di.component.ApplicationComponent
import com.example.omdbsearchapisample.di.component.DaggerApplicationComponent
import com.example.omdbsearchapisample.di.module.ApiInterfaceModule
import com.example.omdbsearchapisample.di.module.AppInjector
import com.example.omdbsearchapisample.di.module.DataBaseModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class ApplicationClass: Application(),HasActivityInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    protected lateinit var mApplicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
//       val mApplicationComponent
////
         mApplicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .dataBaseModule(DataBaseModule(this))
             .apiInterfaceModule(ApiInterfaceModule())
            .build()
        mApplicationComponent.inject(this)


//        AppInjector.init(this)
    }


    fun getComponent(): ApplicationComponent {
        return mApplicationComponent
    }


    override fun activityInjector()=dispatchingAndroidInjector

}