package com.example.apteki.di

import com.example.apteki.ui.branches.branchesInner.employee.EmployeeViewModel
import com.example.apteki.ui.branches.branchesInner.invoice.InvoiceViewModel
import com.example.apteki.ui.branches.branchesInner.trade.TradeViewModel
import com.example.apteki.ui.branches.branchesMain.BranchesViewModel
import com.example.apteki.ui.charts.ChartsViewModel
import com.example.apteki.ui.login.LoginViewModel
import com.example.apteki.ui.map.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { BranchesViewModel(get()) }
    viewModel { TradeViewModel(get()) }
    viewModel { InvoiceViewModel(get()) }
    viewModel { EmployeeViewModel(get()) }
    viewModel { ChartsViewModel(get()) }
    single { SharedViewModel() }
}