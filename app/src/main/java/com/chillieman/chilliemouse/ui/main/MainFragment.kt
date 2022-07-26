package com.chillieman.chilliemouse.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chillieman.chilliemouse.R
import com.chillieman.chilliemouse.databinding.FragmentMainBinding
import com.chillieman.chilliemouse.ui.base.BaseSharedViewModelFragment

class MainFragment: BaseSharedViewModelFragment<MainViewModel>(MainViewModel::class.java) {
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.btnListMode.setOnClickListener {
            sharedViewModel.switchListMode()
        }

        sharedViewModel.isShortListEnabled.observe(viewLifecycleOwner) {
            val string: String
            val btnString: String
            if(it) {
                string = resources.getString(R.string.chillie_list_mode, "SHORT")
                btnString = resources.getString(R.string.chillie_list_mode_to_long)
            } else {
                string = resources.getString(R.string.chillie_list_mode, "LONG")
                btnString = resources.getString(R.string.chillie_list_mode_to_short)
            }
            binding.tvListMode.text = string
            binding.btnListMode.text = btnString
        }

        binding.btnReservation.setOnClickListener {
            sharedViewModel.onReservationButtonPressed()
        }

        binding.btnResetReservations.setOnClickListener {
            sharedViewModel.resetReservationConfirmations()
            Toast.makeText(requireContext(), "Reservations Reset", Toast.LENGTH_SHORT).show()
        }

        sharedViewModel.getListMode()

        return binding.root
    }

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }
}
