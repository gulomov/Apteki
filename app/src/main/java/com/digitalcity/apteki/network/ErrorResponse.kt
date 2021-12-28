package com.digitalcity.apteki.network

import org.json.JSONObject


data class ErrorResponse(
    val jsonResponse: JSONObject = JSONObject(),
    val message: String = "",
    val status: Boolean = false,
    val error: Int = 0
)