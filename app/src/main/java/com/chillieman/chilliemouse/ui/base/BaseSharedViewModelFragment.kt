package com.chillieman.chilliemouse.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseSharedViewModelFragment<T : BaseViewModel>(
    @JvmSuppressWildcards private val sharedViewModelClass: Class<T>
) : BaseFragment() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    val sharedViewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(requireActivity(), viewModelFactory)
            .get(sharedViewModelClass)
    }
        @UiThread
        get
}

