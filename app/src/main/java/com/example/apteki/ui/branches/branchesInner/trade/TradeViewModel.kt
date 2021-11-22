package com.example.apteki.ui.branches.branchesInner.trade

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apteki.network.pojo.TradeResult
import com.example.apteki.repository.Repository
import kotlinx.coroutines.flow.Flow

class TradeViewModel(private val repository: Repository) : ViewModel() {

    fun getTodaysTrade(id: Int): Flow<PagingData<TradeResult>> {
        val newResult: Flow<PagingData<TradeResult>> = repository.todaysTrade(id)
            .cachedIn(viewModelScope)
        return newResult
    }

    fun getTodaysTradeByFilter(
        id: Int, type: String,
        data_start: String,
        data_end: String
    ): Flow<PagingData<TradeResult>> {
        val newResult: Flow<PagingData<TradeResult>> =
            repository.todaysTradeByFilter(id, type, data_start, data_end)
                .cachedIn(viewModelScope)
        return newResult
    }
}