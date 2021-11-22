package com.example.apteki.network.pojo

data class InvoiceResponse(
    val success: Boolean,
    val data: InvoiceData
)

data class InvoiceData(
    val pagination: InvoicePagination,
    val result: List<InvoiceResult>? = emptyList()
)

data class InvoicePagination(
    val next_page: Int? = null,
    val current_page: Int? = null,
    val pervios_page: Int? = null,
    val pages_count: Int? = null,
    val number_of_object: Int? = null
)

data class InvoiceResult(
    val id: Int? = null,
    val branch: Int? = null,
    val warehouse: InvoiceWarehouse,
    val summa: Int? = null,
    val status: Int? = null,
    val date: String?
)

data class InvoiceWarehouse(
    val id: Int?,
    val name: String?
)
