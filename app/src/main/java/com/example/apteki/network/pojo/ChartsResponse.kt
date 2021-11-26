package com.example.apteki.network.pojo

data class ChartsResponse(
    val success: Boolean,
    val data: ChartData
)

data class ChartData(
    val savdo_chart: List<Chart>,
    val fakturo_chart: List<Chart>
)

data class Chart(
    val branch_name: String,
    val summa: Int
)