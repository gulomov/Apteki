package com.digitalcity.apteki.data

import android.content.Context
import android.content.SharedPreferences

private const val APPNAME = "apteki"
private const val PASSWORD = "password"
private const val IS_LOGGED_IN = "is_legged_in"
private const val TOKEN = "token"
private const val COMPANY_TOKEN = "company_token"

private fun getInstance(context: Context): SharedPreferences {
    return context.getSharedPreferences(APPNAME, Context.MODE_PRIVATE)
}

fun Context.saveCompanyToken(token: String) {
    getInstance(this).edit().putString(COMPANY_TOKEN, token).apply()
}

fun Context.getCompanyToken(): String {
    return getInstance(this).getString(COMPANY_TOKEN, "")!!
}

fun Context.saveToken(token: String) {
    getInstance(this).edit().putString(TOKEN, token).apply()
}

fun Context.getToken(): String {
    return getInstance(this).getString(TOKEN, "")!!
}

fun Context.savePassword(password: String) {
    getInstance(this).edit().putString(PASSWORD, password).apply()
}

fun Context.getPassword() {
    getInstance(this).getString(PASSWORD, "")
}

fun Context.setLoggedIn() {
    getInstance(this).edit().putBoolean(IS_LOGGED_IN, true).apply()
}

fun Context.isLoggedIn(): Boolean {
    return getInstance(this).getBoolean(IS_LOGGED_IN, false)
}


