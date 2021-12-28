package com.digitalcity.apteki.network.pojo

data class TradeResponse(
    val success: Boolean,
    val data: TradeData?
)

data class TradeData(
    val pagination: TradePagination,
    val result: List<TradeResult>? = emptyList()
)

data class TradePagination(
    val next_page: Int? = null,
    val current_page: Int? = null,
    val pervios_page: Int? = null,
    val pages_count: Int? = null,
    val number_of_object: Int? = null
)

data class TradeResult(
    val id: Int? = null,
    val total_summa: Int? = null,
    val naqd: Int? = null,
    val plastik: Int? = null,
    val nasiya: Int? = null,
    val comment: String? = null,
    val date: String? = null,
    val branch: String? = null,
    val seller: String? = null,
    val smen: String? = null
)