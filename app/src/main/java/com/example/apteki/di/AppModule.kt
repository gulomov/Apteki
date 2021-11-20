package com.example.apteki.di

import com.example.apteki.repository.Repository
import org.koin.dsl.module

val appModule = module {
    single { Repository(get(), api = get()) }
}