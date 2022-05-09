package com.digitalcity.apteki.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.digitalcity.apteki.network.Api
import com.digitalcity.apteki.network.Resource
import com.digitalcity.apteki.network.pojo.AddBranchRequest
import com.digitalcity.apteki.network.pojo.AddEmployeeRequest
import com.digitalcity.apteki.network.pojo.InvoiceResult
import com.digitalcity.apteki.network.pojo.TradeResult
import com.digitalcity.apteki.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository constructor(
    private val api: Api,
) {
    val COMMENT_PAGING_SIZE = 8
    suspend fun getLogin(username: String, password: String) = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getLogin(username, password) })
    }

    suspend fun getBranches() = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getBranches() })
    }

    suspend fun getCharts() = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getCharts() })
    }

    suspend fun getEmployee(id: Int) = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getEmployee(id) })
    }

    suspend fun getRegions() = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getRegions() })
    }

    suspend fun addBranch(branch: AddBranchRequest) = flow {
        emit(Resource.Loading)
        emit(safeApiCall {
            api.addBranch(
                branch.region,
                branch.name,
                branch.phone,
                branch.address,
                branch.work_time,
                branch.has_dastavka,
                branch.longtitude,
                branch.latitude,
                branch.type
            )
        })
    }

    suspend fun addEmployee(
        addEmployeeRequest: AddEmployeeRequest
    ) = flow {
        emit(Resource.Loading)
        emit(safeApiCall {
            api.addEmployee(
                addEmployeeRequest.full_name,
                addEmployeeRequest.username,
                addEmployeeRequest.password,
                addEmployeeRequest.type,
                addEmployeeRequest.phone,
                addEmployeeRequest.address,
                addEmployeeRequest.branch,
                true
            )
        })

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