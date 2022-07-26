package com.chillieman.chilliemouse.model

class ReservationSelectListItem(
    val type: ReservationSelectListType = ReservationSelectListType.EMPTY,
    val guest: MouseGuest? = null,
    val text: String? = null
)
