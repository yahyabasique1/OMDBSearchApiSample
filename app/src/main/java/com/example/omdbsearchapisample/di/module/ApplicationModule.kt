package com.example.omdbsearchapisample.di.module

import android.app.Application
import android.content.Context
import com.example.omdbsearchapisample.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private var application:Application) {

    @Singleton
    @Provides
    @ApplicationContext
    fun provideContext():Context{

        return application

    }

    @Singleton
    @Provides
    fun applicationContext():Application{
        return application
    }

}