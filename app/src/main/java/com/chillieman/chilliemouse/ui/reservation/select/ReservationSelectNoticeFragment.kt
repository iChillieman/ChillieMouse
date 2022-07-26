package com.chillieman.chilliemouse.ui.reservation.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import com.chillieman.chilliemouse.databinding.FragmentReservationSelectNoticeBinding
import com.chillieman.chilliemouse.ui.base.BaseHybridViewModelFragment
import com.chillieman.chilliemouse.ui.main.MainViewModel
import com.chillieman.chilliemouse.ui.reservation.ReservationViewModel

class ReservationSelectNoticeFragment : BaseHybridViewModelFragment<ReservationViewModel, MainViewModel>(
    ReservationViewModel::class.java,
    MainViewModel::class.java
) {
    private var _binding: FragmentReservationSelectNoticeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationSelectNoticeBinding.inflate(inflater, container, false)

        binding.btnClose.setOnClickListener {
            sharedViewModel.dismissNoReservationsNotice()
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
    }

    companion object {
        const val TAG = "ReservationSelectNotice"
        fun newInstance() = ReservationSelectNoticeFragment()
    }
}
