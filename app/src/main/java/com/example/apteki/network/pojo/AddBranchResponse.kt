package com.example.apteki.network.pojo

data class AddBranchResponse(
    val success: Boolean,
    val error: String? = null,
    val data: AddBranchData
)

data class AddBranchData(
    val id: Int,
    val name: String
)