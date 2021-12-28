package com.digitalcity.apteki.ui.branches.branchesInner.trade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.digitalcity.apteki.network.pojo.TradeResult
import com.digitalcity.apteki.repository.Repository
import kotlinx.coroutines.flow.Flow

class TradeViewModel(private val repository: Repository) : ViewModel() {

    fun getTodaysTrade(id: Int): Flow<PagingData<TradeResult>> {
        return repository.todaysTrade(id).cachedIn(viewModelScope)
    }

    fun getTodaysTradeByFilter(
        id: Int, type: String,
        data_start: String,
        data_end: String
    ): Flow<PagingData<TradeResult>> {
        return repository.todaysTradeByFilter(id, type, data_start, data_end)
            .cachedIn(viewModelScope)
    }
}