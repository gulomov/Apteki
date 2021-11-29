package com.example.apteki.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apteki.network.Api
import com.example.apteki.network.Resource
import com.example.apteki.network.pojo.AddEmployeeRequest
import com.example.apteki.network.pojo.InvoiceResult
import com.example.apteki.network.pojo.TradeResult
import com.example.apteki.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

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
    /*  suspend fun addEmployee(
          addEmployeeRequest: AddEmployeeRequest
      ) = flow {
          emit(Resource.Loading)
          emit(safeApiCall(errorConverter) {
              api.addEmployee(
                  addEmployeeRequest.full_name,
                  addEmployeeRequest.username,
                  addEmployeeRequest.password,
                  addEmployeeRequest.type,
                  addEmployeeRequest.phone,
                  addEmployeeRequest.address,
                  addEmployeeRequest.branch,
              )
          })
      }*/

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