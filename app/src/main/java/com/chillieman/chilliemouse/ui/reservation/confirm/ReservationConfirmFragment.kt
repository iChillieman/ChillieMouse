package com.chillieman.chilliemouse.ui.reservation.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import com.chillieman.chilliemouse.databinding.FragmentReservationConfirmBinding
import com.chillieman.chilliemouse.ui.base.BaseHybridViewModelFragment
import com.chillieman.chilliemouse.ui.main.MainViewModel
import com.chillieman.chilliemouse.ui.reservation.ReservationViewModel

class ReservationConfirmFragment : BaseHybridViewModelFragment<ReservationViewModel, MainViewModel>(
    ReservationViewModel::class.java,
    MainViewModel::class.java
) {
    private var _binding: FragmentReservationConfirmBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationConfirmBinding.inflate(inflater, container, false)

        binding.btnConfirm.setOnClickListener {
            viewModel.confirmCurrentSelection()
            sharedViewModel.onReservationConfirmed()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
    }

    companion object {
        const val TAG = "ReservationConfirm"
        fun newInstance() = ReservationConfirmFragment()
    }
}

