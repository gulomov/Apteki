package com.digitalcity.apteki.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.digitalcity.apteki.network.Api
import com.digitalcity.apteki.network.pojo.TradeResponse
import com.digitalcity.apteki.network.pojo.TradeResult
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class TodaysTradePaginSource(
    private val api: Api,
    val id: Int,
    private val type: String?,
    private val date_start: String?,
    private val date_end: String?
) :
    PagingSource<Int, TradeResult>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TradeResult> {
        val position = params.key ?: STARTING_PAGE_INDEX
        var response: TradeResponse

        return try {
            response = if (type == "filter") api.getTradeByFilter(
                position,
                id,
                date_start.toString(),
                date_end.toString()
            )
            else api.getTrades(position, id)
            val feedPosts = response.data?.result

            LoadResult.Page(
                data = feedPosts!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.data?.pagination?.current_page == response.data?.pagination?.pages_count) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, TradeResult>): Int? {
        TODO("Not yet implemented")
    }
}