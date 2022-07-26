package com.chillieman.chilliemouse.ui.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chillieman.chilliemouse.model.ConfirmStatus
import com.chillieman.chilliemouse.model.MouseGuest
import com.chillieman.chilliemouse.repository.GuestRepository
import com.chillieman.chilliemouse.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReservationViewModel
@Inject constructor(
    private val guestRepository: GuestRepository
) : BaseViewModel() {
    var selectedGuests: Set<MouseGuest> = emptySet()

    private val _isDismissNote = MutableLiveData<Boolean>()
    val isDismissNote: LiveData<Boolean>
        get() = _isDismissNote

    private val _guestList = MutableLiveData<List<MouseGuest>>()
    val guestList: LiveData<List<MouseGuest>>
        get() = _guestList

    private val _confirmStatus = MutableLiveData<ConfirmStatus>()
    val confirmStatus: LiveData<ConfirmStatus>
        get() = _confirmStatus

    fun updateSelectedGuests(guests: Set<MouseGuest>) {
        if (guests.isEmpty()) {
            _confirmStatus.value = ConfirmStatus.NO_SELECTION
        } else if (guests.all { it.hasReservation }) {
            _confirmStatus.value = ConfirmStatus.PERFECT
        } else if (guests.any { it.hasReservation }) {
            _confirmStatus.value = ConfirmStatus.CONFLICT
        } else {
            _confirmStatus.value = ConfirmStatus.NO_RESERVATION
        }

        selectedGuests = guests
    }

    fun refreshGuestList() {
        guestRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                selectedGuests = emptySet()
                _confirmStatus.value = ConfirmStatus.NO_SELECTION
                _guestList.value = it
                Log.d(TAG, "Guests Loaded!")
            }, {
                Log.e(TAG, "Could not fetch Guests", it)
            }).disposeOnClear()
    }

    fun canContinue(): Boolean = when (_confirmStatus.value) {
        ConfirmStatus.CONFLICT,
        ConfirmStatus.PERFECT -> true
        else -> {
            _confirmStatus.value = ConfirmStatus.NO_RESERVATION
            false
        }
    }

    fun confirmCurrentSelection() {
        guestRepository.confirmGuests(selectedGuests.toList())
        refreshGuestList()
    }


    companion object {
        private const val TAG = "ReservationViewModel"
    }

}
