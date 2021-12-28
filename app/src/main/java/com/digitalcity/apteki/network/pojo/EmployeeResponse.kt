package com.digitalcity.apteki.network.pojo

data class EmployeeResponse(
    val success: Boolean,
    val data: List<EmployeeData>
)

data class EmployeeData(
    val id: Int?,
    val full_name: String?,
    val type: String?,
    val phone: String?,
    val address: String?,
    val company: Int?,
    val branch: EmployeeBranch?,
)

data class EmployeeBranch(
    val id: Int?,
    val name: String?
)
