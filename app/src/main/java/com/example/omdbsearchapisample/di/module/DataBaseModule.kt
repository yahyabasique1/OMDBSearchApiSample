package com.example.omdbsearchapisample.di.module

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import com.example.omdbsearchapisample.RoomDB.AppDatabase
import com.example.omdbsearchapisample.RoomDB.BookMarkDao
import com.example.omdbsearchapisample.di.ApplicationContext
import com.example.omdbsearchapisample.di.DatabaseInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule(private var context: Context) {



    @DatabaseInfo
    var databaseName="bookmark.db"




    @Provides
    fun provideDatabase():AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).allowMainThreadQueries()
            .build()
    }

    @Provides
    @DatabaseInfo
    fun ovideDatabseName():String{
        return databaseName
    }


    @Singleton
    @Provides
    fun provideBookMarkDao(appDatabase: AppDatabase):BookMarkDao{
        return appDatabase.BookMarkDao()
    }




}