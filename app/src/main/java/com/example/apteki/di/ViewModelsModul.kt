package com.example.apteki.di

import com.example.apteki.ui.branches.branchesMain.BranchesViewModel
import com.example.apteki.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { BranchesViewModel(get()) }
}