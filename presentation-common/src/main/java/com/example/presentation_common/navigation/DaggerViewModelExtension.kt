package com.example.presentation_common.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified T : ViewModel> daggerViewModel(
    owner: ViewModelStoreOwner,
    factory: ViewModelProvider.Factory
): T = viewModel(
    modelClass = T::class,
    viewModelStoreOwner = owner,
    factory = factory
)