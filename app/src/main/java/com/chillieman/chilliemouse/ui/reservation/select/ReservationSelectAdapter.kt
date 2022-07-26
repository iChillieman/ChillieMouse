package com.chillieman.chilliemouse.ui.reservation.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chillieman.chilliemouse.R
import com.chillieman.chilliemouse.databinding.ItemReservationGuestBinding
import com.chillieman.chilliemouse.databinding.ItemReservationHeaderBinding
import com.chillieman.chilliemouse.databinding.ItemReservationInfoBinding
import com.chillieman.chilliemouse.model.MouseGuest
import com.chillieman.chilliemouse.model.ReservationSelectListItem
import com.chillieman.chilliemouse.model.ReservationSelectListType

class ReservationSelectAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), HeaderItemDecoration.StickyHeaderInterface {
    private var listOfReservations: List<ReservationSelectListItem> = emptyList()

    private val firstHeaderPosition = 0
    private var secondHeaderPosition: Int? = null
    val selectedGuests: MutableSet<MouseGuest> = mutableSetOf()

    fun listUpdate(list: List<ReservationSelectListItem>) {
        secondHeaderPosition = null
        list.forEachIndexed { index, item ->
            if (index != firstHeaderPosition && item.type == ReservationSelectListType.HEADER) {
                secondHeaderPosition = index
            }
        }
        listOfReservations = list
        selectedGuests.clear()

        //TODO: Use Util library before production
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ReservationSelectListType.GUEST.ordinal -> {
                val binding = ItemReservationGuestBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GuestViewHolder(binding)
            }
            ReservationSelectListType.HEADER.ordinal -> {
                val binding = ItemReservationHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)

            }
            ReservationSelectListType.INFO.ordinal -> {
                val binding = ItemReservationInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                InfoViewHolder(binding)
            }
            else -> {
                throw IllegalStateException("Cannot inflate an empty reservation item.")
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val text = listOfReservations[position].text
                    ?: throw IllegalStateException("Reservation Header doesnt have text")
                holder.bind(text)
            }

            is GuestViewHolder -> {
                val guest = listOfReservations[position].guest
                    ?: throw IllegalStateException("Reservation doesnt have a guest")
                holder.bind(guest)
            }

            is InfoViewHolder -> {
                val text = listOfReservations[position].text
                    ?: throw IllegalStateException("Reservation Header doesnt have text")
                holder.bind(text)
            }
        }
    }

    override fun getItemCount(): Int = listOfReservations.size

    override fun getItemViewType(position: Int): Int {
        return listOfReservations[position].type.ordinal
    }

    interface Listener {
        fun onSelectionChanged(guests: Set<MouseGuest>)
    }

    inner class HeaderViewHolder(
        val binding: ItemReservationHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: String) {
            binding.header.text = header
//            binding.header.contentDescription = header
            binding.header.announceForAccessibility(header)
        }
    }

    inner class GuestViewHolder(
        private val binding: ItemReservationGuestBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(guest: MouseGuest) {
            val existsInSet = selectedGuests.any { it.id == guest.id }
            binding.chkGuest.isChecked = selectedGuests.any { it.id == guest.id }
//            binding.chkGuest.isChecked = selectedGuests.contains(guest)
            binding.chkGuest.setOnClickListener {
                if (!selectedGuests.add(guest)) {
                    selectedGuests.remove(guest)
                }
                listener.onSelectionChanged(selectedGuests)
            }
//            binding.chkGuest.setOnCheckedChangeListener { _, isChecked ->
//                Log.d("Chillie", "Check Changed!")
//                if (isChecked) {
//                    selectedGuests.add(guest)
//                } else {
//                    selectedGuests.remove(guest)
//                }
//                listener.onSelectionChanged(selectedGuests)
//            }
            binding.chkGuest.text = guest.fullName
        }
    }

    inner class InfoViewHolder(
        private val binding: ItemReservationInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: String) {
            binding.info.text = message
        }
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        return with(secondHeaderPosition) {
            if (this == null) {
                firstHeaderPosition
            } else if (itemPosition >= this) {
                this
            } else {
                firstHeaderPosition
            }
        }
    }

    override fun getHeaderLayout(headerPosition: Int): Int = R.layout.item_reservation_header


    override fun bindHeaderData(header: View?, headerPosition: Int) {
        header?.findViewById<TextView>(R.id.header)?.text = listOfReservations[headerPosition].text
    }

    override fun isHeader(itemPosition: Int): Boolean =
        listOfReservations[itemPosition].type == ReservationSelectListType.HEADER

}

