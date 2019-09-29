package com.example.omdbsearchapisample.di

import androidx.fragment.app.Fragment
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.reflect.KClass

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseInfo

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope(val value: KClass<out Fragment>)