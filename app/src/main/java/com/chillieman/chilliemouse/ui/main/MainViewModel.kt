package com.chillieman.chilliemouse.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliemouse.model.PageSelection
import com.chillieman.chilliemouse.repository.GuestRepository
import com.chillieman.chilliemouse.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val guestRepository: GuestRepository
) : BaseViewModel() {
    private val _pageSelection =
        MutableLiveData<PageSelection>().apply { value = PageSelection.MAIN }
    val pageSelection: LiveData<PageSelection>
        get() = _pageSelection

    private val _isShortListEnabled = MutableLiveData<Boolean>()
    val isShortListEnabled: LiveData<Boolean>
        get() = _isShortListEnabled


    fun onReservationButtonPressed() {
        _pageSelection.value = PageSelection.RESERVATIONS
    }

    fun switchListMode() {
        guestRepository.switchListMode()
        getListMode()
    }

    fun getListMode() {
        _isShortListEnabled.value = guestRepository.isShortListEnabled
    }

    fun resetReservationConfirmations() {
        guestRepository.resetConfirmations()
    }

    fun gotoReservationConfirmScreen(isConflict: Boolean) {
        _pageSelection.value = if(isConflict) {
            PageSelection.RESERVATIONS_CONFLICT
        } else {
            PageSelection.RESERVATIONS_CONFIRM
        }
    }

    fun onReservationConfirmed() {
        _pageSelection.value = PageSelection.RESERVATIONS
    }

    fun onReservationBackButton() {
        _pageSelection.value = PageSelection.MAIN
    }

    fun onReservationRequiredError() {
        _pageSelection.value = PageSelection.RESERVATIONS_NEEDED
    }

    fun dismissNoReservationsNotice() {
        if(_pageSelection.value == PageSelection.RESERVATIONS_NEEDED) {
            _pageSelection.value = PageSelection.RESERVATIONS
        }
    }

    fun onReturnToMain() {
        _pageSelection.value = PageSelection.MAIN
    }
}
