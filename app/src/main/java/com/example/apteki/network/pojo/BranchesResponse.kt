package com.example.apteki.network.pojo

data class BranchesResponse(
    val success: Boolean,
    val data: ArrayList<BranchesData>
)

data class BranchesData(
    val id: Int,
    val name: String,
    val summa: Int
)