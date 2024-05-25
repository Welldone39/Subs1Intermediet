package com.wildan.subs1intermediet.Module

import com.wildan.subs1intermediet.ui.ViewModel.ViewModelAddStory
import com.wildan.subs1intermediet.ui.ViewModel.ViewModelLogin
import com.wildan.subs1intermediet.ui.ViewModel.ViewModelRegister
import com.wildan.subs1intermediet.ui.ViewModel.ViewModelStory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val moduleViewModel = module {
    viewModel { ViewModelRegister(get()) }
    viewModel { ViewModelLogin(get()) }
    viewModel { ViewModelStory(get()) }
    viewModel { ViewModelAddStory(get()) }
}