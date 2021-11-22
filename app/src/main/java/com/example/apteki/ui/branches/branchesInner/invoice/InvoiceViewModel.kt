package com.example.apteki.ui.branches.branchesInner.invoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apteki.network.pojo.InvoiceResult
import com.example.apteki.repository.Repository
import kotlinx.coroutines.flow.Flow

class InvoiceViewModel(private val repository: Repository) : ViewModel() {
    fun getTodaysInvoice(id: Int): Flow<PagingData<InvoiceResult>> {
        val newResult: Flow<PagingData<InvoiceResult>> = repository.todaysInovoice(id)
            .cachedIn(viewModelScope)
        return newResult
    }

    fun getTodaysInvoiceByFilter(
        id: Int, type: String,
        data_start: String,
        data_end: String
    ): Flow<PagingData<InvoiceResult>> {
        val newResult: Flow<PagingData<InvoiceResult>> =
            repository.todaysInvoiceByFilter(id, type, data_start, data_end)
                .cachedIn(viewModelScope)
        return newResult
    }
}