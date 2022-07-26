package com.chillieman.chilliemouse.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.chillieman.chilliemouse.ChillieApplication
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(app: ChillieApplication): Context

    @Binds
    abstract fun bindViewModelFactory(vmFactory: ViewModelFactory): ViewModelProvider.Factory
}
