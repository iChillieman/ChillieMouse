package com.chillieman.chilliemouse.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    protected fun <T : Disposable> T.disposeOnClear(): T {
        disposables.add(this)
        return this
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

