package com.chillieman.chilliemouse.model

class MouseGuest(val first: String, val last: String, val hasReservation: Boolean, val id: Long) {
    val fullName get() = "${first.trim()} ${last.trim()}"
}
