package com.digitalcity.apteki.network.pojo

data class AddEmployeeRequest(
    val full_name: String,
    val username: String,
    val password: String,
    val type: String,
    val phone: String,
    val address: String,
    val branch: String,
    val is_active:Boolean
)