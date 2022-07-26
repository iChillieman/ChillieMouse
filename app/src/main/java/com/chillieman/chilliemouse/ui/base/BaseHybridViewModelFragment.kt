package com.chillieman.chilliemouse.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseHybridViewModelFragment<T : BaseViewModel, S : BaseViewModel>(
    @JvmSuppressWildcards private val viewModelClass: Class<T>,
    @JvmSuppressWildcards private val sharedViewModelClass: Class<S>
) : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(requireActivity(), viewModelFactory)[viewModelClass]
    }
        @UiThread
        get

    val sharedViewModel: S by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(requireActivity(), viewModelFactory)[sharedViewModelClass]
    }
        @UiThread
        get
}
