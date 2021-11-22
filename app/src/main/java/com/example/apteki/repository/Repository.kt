package com.example.apteki.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apteki.network.Api
import com.example.apteki.network.ErrorResponse
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.InvoiceResult
import com.example.apteki.network.pojo.TradeResult
import com.example.apteki.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Converter

class Repository constructor(
    var errorConverter: Converter<ResponseBody, ErrorResponse>,
    private val api: Api,
) {
    val COMMENT_PAGING_SIZE = 8
    suspend fun getLogin(username: String, password: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall(errorConverter) { api.getLogin(username, password) })
    }

    suspend fun getBranches() = flow {
        emit(Resource.Loading)
        emit(safeApiCall(errorConverter) { api.getBranches() })
    }

    suspend fun getEmployee(id: Int) = flow {
        emit(Resource.Loading)
        emit(safeApiCall(errorConverter) { api.getEmployee(id) })
    }

    fun todaysTrade(id: Int): Flow<PagingData<TradeResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = COMMENT_PAGING_SIZE
            ),
            pagingSourceFactory = { TodaysTradePaginSource(api, id, "", "", "") }

        ).flow

    }

    fun todaysTradeByFilter(
        id: Int,
        type: String,
        data_start: String,
        data_end: String
    ): Flow<PagingData<TradeResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = COMMENT_PAGING_SIZE
            ),
            pagingSourceFactory = { TodaysTradePaginSource(api, id, type, data_start, data_end) }
        ).flow
    }

    fun todaysInovoice(id: Int): Flow<PagingData<InvoiceResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = COMMENT_PAGING_SIZE
            ),
            pagingSourceFactory = { InvoicePagingSource(api, id, "", "", "") }
        ).flow
    }

    fun todaysInvoiceByFilter(
        id: Int,
        type: String,
        data_start: String,
        data_end: String
    ): Flow<PagingData<InvoiceResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = COMMENT_PAGING_SIZE
            ),
            pagingSourceFactory = { InvoicePagingSource(api, id, type, data_start, data_end) }
        ).flow
    }

}