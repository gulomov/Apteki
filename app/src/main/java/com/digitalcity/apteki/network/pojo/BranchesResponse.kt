package com.digitalcity.apteki.network.pojo

data class BranchesResponse(
    val success: Boolean,
    val data: List<BranchesData>
)

data class BranchesData(
    val id: Int,
    val name: String,
    val summa: Int
)