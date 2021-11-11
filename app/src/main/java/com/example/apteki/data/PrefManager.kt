package com.example.apteki.data

import android.content.Context
import android.content.SharedPreferences

private const val APPNAME = "apteki"
private const val PASSWORD = "password"
private const val IS_LOGGED_IN = "is_legged_in"

private fun getInstance(context: Context): SharedPreferences {
    return context.getSharedPreferences(APPNAME, Context.MODE_PRIVATE)
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


