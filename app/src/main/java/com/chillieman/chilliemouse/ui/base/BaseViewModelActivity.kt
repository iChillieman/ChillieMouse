package com.chillieman.chilliemouse.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseViewModelActivity<T : BaseViewModel>(
    @JvmSuppressWildcards private val viewModelClass: Class<T>
) : BaseActivity() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory)[viewModelClass]
    }
        @UiThread
        get
}

