package com.example.apteki.network.pojo

data class RegionsResponse(
    val success: Boolean,
    val error: String,
    val message: String,
    val data: List<RegionsData>
)

data class RegionsData(
    val id: Int,
    val name: String
)
