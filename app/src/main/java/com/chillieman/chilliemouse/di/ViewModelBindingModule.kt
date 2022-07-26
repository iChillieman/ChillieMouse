package com.chillieman.chilliemouse.di

import androidx.lifecycle.ViewModel
import com.chillieman.chilliemouse.di.annotation.ViewModelKey
import com.chillieman.chilliemouse.ui.main.MainViewModel
import com.chillieman.chilliemouse.ui.reservation.ReservationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReservationViewModel::class)
    abstract fun bindReservationViewModel(viewModel: ReservationViewModel): ViewModel
}
