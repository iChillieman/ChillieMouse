package com.chillieman.chilliemouse.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory
@Inject constructor(
    private val providers: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var provider: Provider<out ViewModel>? = providers[modelClass]

        if (provider == null) {
            // If there isn't an exact match, search for classes that are castable to it.
            for ((key, value) in providers) {
                if (modelClass.isAssignableFrom(key)) {
                    provider = value
                    break
                }
            }

            if (provider == null) {
                throw IllegalArgumentException("Unknown ViewModel class. Was it annotated properly? Got: $modelClass")
            }
        }

        return modelClass.cast(provider.get())!!
    }
}
