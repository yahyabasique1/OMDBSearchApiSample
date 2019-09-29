package com.example.omdbsearchapisample.di.module

import com.example.omdbsearchapisample.di.FragmentScope
import com.example.omdbsearchapisample.fragment.MovieListFragment
import com.example.omdbsearchapisample.fragment.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope(MovieListFragment::class)
    @ContributesAndroidInjector
    abstract fun getMovieListFragment():MovieListFragment

    @FragmentScope(SearchFragment::class)
    @ContributesAndroidInjector
    abstract fun getSearchFragment():SearchFragment

}