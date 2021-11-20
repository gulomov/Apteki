package com.example.apteki.network.pojo

import android.provider.ContactsContract
import com.example.apteki.network.Resource

data class LoginResponse(
    val success: Boolean,
    val error: String,
    val message: String,
    val data: LoginData
)

data class LoginData(
    val user: LoginUser,
    val token: String
)

data class LoginUser(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val company: LoginCompany
)

data class LoginCompany(
    val id: Int,
    val name: String,
    val phone: String?,
    val email: String?,
    val token: String
)