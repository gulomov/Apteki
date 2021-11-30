package com.example.apteki.network.pojo


data class AddBranchRequest(
    val region: String,
    val name: String,
    val phone: String,
    val address: String,
    val work_time: String,
    val has_dastavka: String,
    val latitude: String,
    val longtitude: String,
    val type: String
)