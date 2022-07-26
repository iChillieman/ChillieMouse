package com.chillieman.chilliemouse.di

import com.chillieman.chilliemouse.ChillieApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ViewModelBindingModule::class
    ]
)

interface ApplicationComponent : AndroidInjector<ChillieApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: ChillieApplication): Builder

        fun build(): ApplicationComponent
    }
}
