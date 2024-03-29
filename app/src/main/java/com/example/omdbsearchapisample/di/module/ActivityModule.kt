package com.example.omdbsearchapisample.di.module

import com.example.omdbsearchapisample.MainActivity
import com.example.omdbsearchapisample.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMovieDetailActivity(): MovieDetailActivity

}