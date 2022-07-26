package com.chillieman.chilliemouse.di

import com.chillieman.chilliemouse.di.annotation.ActivityScoped
import com.chillieman.chilliemouse.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}
