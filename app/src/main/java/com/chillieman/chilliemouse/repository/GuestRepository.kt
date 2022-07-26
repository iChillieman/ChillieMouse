package com.chillieman.chilliemouse.repository

import com.chillieman.chilliemouse.model.MouseGuest
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GuestRepository
@Inject constructor(
    //TODO: Use DAOs to interact with DB
) {
    private val confirmedGuests = mutableSetOf<Long>()
    var isShortListEnabled = true

    fun getAll(): Single<List<MouseGuest>> = if (isShortListEnabled) {
        getAllGuestsSortedSmallList()
    } else {
        getAllGuestsSorted()
    }

    fun switchListMode() {
        isShortListEnabled = !isShortListEnabled
    }

    private fun getAllGuestsSorted(): Single<List<MouseGuest>> {
        //Play Data
        return Single.just(
            listOf(
                MouseGuest("Chillie", "Man", true, 1),
                MouseGuest("Mickey", "Mouse", true, 2),
                MouseGuest("Minnie", "Mouse", true, 3),
                MouseGuest("Simba", "", true, 4),
                MouseGuest("Elsa", "", true, 5),
                MouseGuest("Goofy", "", true, 6),
                MouseGuest("Pluto", "", false, 7),
                MouseGuest("Donald", "Duck", true, 8),
                MouseGuest("Daisy", "Duck", false, 9),
                MouseGuest("Tinker", "Bell", true, 10),
                MouseGuest("Peter", "Pan", true, 11),
                MouseGuest("Captain", "Hook", false, 12),
                MouseGuest("Woody", "", true, 13),
                MouseGuest("Buzz", "Lightyear", true, 14),
                MouseGuest("Kuzco", "", false, 15),
                MouseGuest("Stitch", "", true, 16),
                MouseGuest("Winnie the", "Pooh", true, 17),
                MouseGuest("Tigger", "", false, 18),
                MouseGuest("Stan", "Marsh", false, 19),
                MouseGuest("Big", "Boy", false, 20),
                MouseGuest("Chillie", "Man", true, 21),
                MouseGuest("Mickey", "Mouse", true, 22),
                MouseGuest("Minnie", "Mouse", true, 23),
                MouseGuest("Simba", "", true, 24),
                MouseGuest("Elsa", "", true, 25),
                MouseGuest("Goofy", "", true, 26),
                MouseGuest("Pluto", "", false, 27),
                MouseGuest("Donald", "Duck", true, 28),
                MouseGuest("Daisy", "Duck", false, 29),
                MouseGuest("Tinker", "Bell", true, 30),
                MouseGuest("Peter", "Pan", true, 31),
                MouseGuest("Captain", "Hook", false, 32),
                MouseGuest("Woody", "", true, 33),
                MouseGuest("Buzz", "Lightyear", true, 34),
                MouseGuest("Kuzco", "", false, 35),
                MouseGuest("Stitch", "", true, 36),
                MouseGuest("Winnie the", "Pooh", true, 37),
                MouseGuest("Tigger", "", false, 38),
                MouseGuest("Stan", "Marsh", false, 39),
                MouseGuest("Big", "Boy", false, 40),
            ).filterNot {
                confirmedGuests.contains(it.id)
            }
        )
    }

    private fun getAllGuestsSortedSmallList(): Single<List<MouseGuest>> {
        //Play Data
        return Single.just(
            listOf(
                MouseGuest("Chillie", "Man", true, 1),
                MouseGuest("Mickey", "Mouse", true, 2),
                MouseGuest("Minnie", "Mouse", true, 3),
                MouseGuest("Simba", "", true, 4),
                MouseGuest("Elsa", "", true, 5),
                MouseGuest("Goofy", "", true, 6),
                MouseGuest("Pluto", "", false, 7),
                MouseGuest("Donald", "Duck", false, 8),
                MouseGuest("Daisy", "Duck", true, 9),
                MouseGuest("Tinker", "Bell", true, 10),
                MouseGuest("Peter", "Pan", false, 11),
                MouseGuest("Captain", "Hook", true, 12),
                MouseGuest("Woody", "", true, 13),
                MouseGuest("Buzz", "Lightyear", false, 14),
                MouseGuest("Kuzco", "", true, 15),
                MouseGuest("Stitch", "", true, 16),
                MouseGuest("Winnie the", "Pooh", true, 17),
                MouseGuest("Tigger", "", true, 18)
            ).filterNot {
                confirmedGuests.contains(it.id)
            }
        )
    }

    fun confirmGuests(guests: List<MouseGuest>) {
        guests.forEach {
            confirmedGuests.add(it.id)
        }
    }

    fun resetConfirmations() = confirmedGuests.clear()
}
