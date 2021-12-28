package com.digitalcity.apteki.di

import com.digitalcity.apteki.repository.Repository
import org.koin.dsl.module

val appModule = module {
    single { Repository( api = get()) }
}