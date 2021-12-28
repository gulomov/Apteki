package com.digitalcity.apteki.network.pojo

data class AddEmployeeResponse(
    val success: Boolean,
    val data: AddEmployeeData?,
    val error: String? = null
)

data class AddEmployeeData(
    val id: Int?,
    val full_name: String?,
    val type: String?,
    val phone: String?,
    val address: String?,
    val company: Int?,
    val branch: AddEmployeeBranch?,
)

data class AddEmployeeBranch(
    val id: Int?,
    val name: String?
)