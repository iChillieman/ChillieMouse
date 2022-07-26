package com.chillieman.chilliemouse.di

import com.chillieman.chilliemouse.di.annotation.FragmentScoped
import com.chillieman.chilliemouse.ui.main.MainFragment
import com.chillieman.chilliemouse.ui.reservation.confirm.ReservationConfirmFragment
import com.chillieman.chilliemouse.ui.reservation.conflict.ReservationConflictFragment
import com.chillieman.chilliemouse.ui.reservation.select.ReservationSelectFragment
import com.chillieman.chilliemouse.ui.reservation.select.ReservationSelectNoticeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindMainFragment(): MainFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindReservationConfirmFragment(): ReservationConfirmFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindReservationConflictFragment(): ReservationConflictFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindReservationSelectGuestsFragment(): ReservationSelectFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindReservationSelectNoticeFragment(): ReservationSelectNoticeFragment
}
