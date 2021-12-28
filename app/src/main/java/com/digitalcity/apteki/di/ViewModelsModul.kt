package com.digitalcity.apteki.di

import com.digitalcity.apteki.ui.branches.branchesInner.employee.EmployeeViewModel
import com.digitalcity.apteki.ui.branches.branchesInner.invoice.InvoiceViewModel
import com.digitalcity.apteki.ui.branches.branchesInner.trade.TradeViewModel
import com.digitalcity.apteki.ui.branches.branchesMain.BranchesViewModel
import com.digitalcity.apteki.ui.charts.ChartsViewModel
import com.digitalcity.apteki.ui.login.LoginViewModel
import com.digitalcity.apteki.ui.map.SharedViewModel
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