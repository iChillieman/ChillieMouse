package com.chillieman.chilliemouse.ui.main


import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.chillieman.chilliemouse.R
import com.chillieman.chilliemouse.databinding.ActivityMainBinding
import com.chillieman.chilliemouse.model.PageSelection
import com.chillieman.chilliemouse.ui.base.BaseViewModelActivity
import com.chillieman.chilliemouse.ui.reservation.confirm.ReservationConfirmFragment
import com.chillieman.chilliemouse.ui.reservation.conflict.ReservationConflictFragment
import com.chillieman.chilliemouse.ui.reservation.select.ReservationSelectFragment
import com.chillieman.chilliemouse.ui.reservation.select.ReservationSelectNoticeFragment

class MainActivity : BaseViewModelActivity<MainViewModel>(MainViewModel::class.java) {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.pageSelection.observe(this) {
            when (it) {
                PageSelection.MAIN -> {
                    if(supportFragmentManager.findFragmentByTag(MainFragment.TAG) != null) {
                        supportFragmentManager.popBackStack(MainFragment.TAG, 0)
                    } else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_in_left,
                                R.anim.slide_out_left,
                                R.anim.slide_in_left,
                                R.anim.slide_out_left
                            )
                            .addToBackStack(MainFragment.TAG)
                            .replace(binding.container.id, MainFragment.newInstance(), MainFragment.TAG)
                            .commit()
                    }
                }

                PageSelection.RESERVATIONS -> {
                    //If the notice is showing, pop backstack instead of replacing fragment
                    if (
                        supportFragmentManager.findFragmentByTag(ReservationSelectNoticeFragment.TAG) != null ||
                        supportFragmentManager.findFragmentByTag(ReservationConfirmFragment.TAG) != null ||
                        supportFragmentManager.findFragmentByTag(ReservationConflictFragment.TAG) != null

                    ) {
                        supportFragmentManager.popBackStack()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_in_left,
                                R.anim.slide_out_left,
                                R.anim.slide_in_left,
                                R.anim.slide_out_down
                            )
                            .replace(
                                binding.container.id,
                                ReservationSelectFragment.newInstance(),
                                ReservationSelectFragment.TAG
                            )
                            .addToBackStack(ReservationSelectFragment.TAG)
                            .commit()
                    }
                }

                PageSelection.RESERVATIONS_NEEDED -> {
                    if (supportFragmentManager.findFragmentByTag(ReservationSelectNoticeFragment.TAG) == null) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.slide_in_up,
                                R.anim.slide_out_down,
                                R.anim.slide_in_up,
                                R.anim.slide_out_down
                            )
                            .add(
                                binding.container.id,
                                ReservationSelectNoticeFragment.newInstance(),
                                ReservationSelectNoticeFragment.TAG
                            )
                            .addToBackStack(ReservationSelectNoticeFragment.TAG)
                            .commit()
                    }
                }

                PageSelection.RESERVATIONS_CONFIRM -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in_up,
                            R.anim.slide_out_down,
                            R.anim.slide_in_up,
                            R.anim.slide_out_down
                        )
                        .replace(
                            binding.container.id,
                            ReservationConfirmFragment.newInstance(),
                            ReservationConfirmFragment.TAG
                        )
                        .addToBackStack(ReservationConfirmFragment.TAG)
                        .commit()
                }

                PageSelection.RESERVATIONS_CONFLICT -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in_up,
                            R.anim.slide_out_down,
                            R.anim.slide_in_up,
                            R.anim.slide_out_down
                        )
                        .replace(
                            binding.container.id,
                            ReservationConflictFragment.newInstance(),
                            ReservationConflictFragment.TAG
                        )
                        .addToBackStack(ReservationConflictFragment.TAG)
                        .commit()
                }

                else -> Unit
            }
        }
    }

    override fun onBackPressed() {
        when (viewModel.pageSelection.value) {
            PageSelection.RESERVATIONS,
            PageSelection.RESERVATIONS_NEEDED -> {
                supportFragmentManager.popBackStack(
                    ReservationSelectFragment.TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                viewModel.onReturnToMain()
            }

            PageSelection.MAIN -> finish()
            else -> super.onBackPressed()
        }
    }
}
