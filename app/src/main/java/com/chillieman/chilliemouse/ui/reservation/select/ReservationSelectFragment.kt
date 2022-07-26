package com.chillieman.chilliemouse.ui.reservation.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillieman.chilliemouse.R
import com.chillieman.chilliemouse.databinding.FragmentReservationSelectBinding
import com.chillieman.chilliemouse.model.*
import com.chillieman.chilliemouse.ui.base.BaseHybridViewModelFragment
import com.chillieman.chilliemouse.ui.main.MainViewModel
import com.chillieman.chilliemouse.ui.reservation.ReservationViewModel

class ReservationSelectFragment : BaseHybridViewModelFragment<ReservationViewModel, MainViewModel>(
    ReservationViewModel::class.java,
    MainViewModel::class.java
), ReservationSelectAdapter.Listener {
    private lateinit var adapter: ReservationSelectAdapter

    private var _binding: FragmentReservationSelectBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationSelectBinding.inflate(inflater, container, false)

        viewModel.confirmStatus.observe(viewLifecycleOwner) {
            binding.btnConfirm.isEnabled = it != ConfirmStatus.NO_SELECTION
        }

        viewModel.isDismissNote.observe(viewLifecycleOwner) {
            binding.btnConfirm.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }

        binding.btnBack.setOnClickListener {
            sharedViewModel.onReservationBackButton()
        }

        viewModel.guestList.observe(viewLifecycleOwner) { guestList ->
            val reservationHeader = resources.getString(R.string.reservation_select_reservation)

            val list = mutableListOf(
                ReservationSelectListItem(
                    type = ReservationSelectListType.HEADER,
                    text = reservationHeader
                )
            )

            guestList.filter { it.hasReservation }.forEach { guest ->
                list.add(
                    ReservationSelectListItem(
                        type = ReservationSelectListType.GUEST,
                        guest = guest
                    )
                )
            }

            val nonReservations = guestList.filter { !it.hasReservation }
            if (nonReservations.isNotEmpty()) {
                list.add(
                    ReservationSelectListItem(
                        type = ReservationSelectListType.HEADER,
                        text = resources.getString(R.string.reservation_select_no_reservation)
                    )
                )

                nonReservations.forEach {
                    list.add(
                        ReservationSelectListItem(
                            type = ReservationSelectListType.GUEST,
                            guest = it
                        )
                    )
                }
            }

            list.add(
                ReservationSelectListItem(
                    type = ReservationSelectListType.INFO,
                    text = resources.getString(R.string.reservation_select_list_info)
                )
            )

            adapter.listUpdate(list)

            binding.rvGuestList.scrollToPosition(0)
        }

        binding.btnConfirm.setOnClickListener {
            if (viewModel.canContinue()) {
                viewModel.confirmStatus.value?.let {
                    when (it) {
                        ConfirmStatus.CONFLICT -> sharedViewModel.gotoReservationConfirmScreen(
                            isConflict = true
                        )
                        ConfirmStatus.PERFECT -> sharedViewModel.gotoReservationConfirmScreen(
                            isConflict = false
                        )
                        else -> Unit
                    }
                }
            } else {
                sharedViewModel.onReservationRequiredError()
            }
        }

        adapter = ReservationSelectAdapter(this)

        binding.rvGuestList.adapter = adapter
        binding.rvGuestList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGuestList.addItemDecoration(
            HeaderItemDecoration(binding.rvGuestList, adapter)
        )

        viewModel.refreshGuestList()

        sharedViewModel.pageSelection.observe(viewLifecycleOwner) {
            if (it == PageSelection.RESERVATIONS_NEEDED) {
                binding.btnConfirm.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                binding.btnConfirm.isEnabled = false
            } else if (it == PageSelection.RESERVATIONS) {
                binding.btnConfirm.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
                binding.btnConfirm.isEnabled = viewModel.confirmStatus.value != ConfirmStatus.NO_SELECTION
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
    }

    override fun onSelectionChanged(guests: Set<MouseGuest>) {
        viewModel.updateSelectedGuests(guests)
        sharedViewModel.dismissNoReservationsNotice()
    }

    companion object {
        const val TAG = "ReservationSelect"
        fun newInstance() = ReservationSelectFragment()
    }
}

