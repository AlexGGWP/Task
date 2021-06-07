package com.tryzens.task.di

import org.koin.core.module.Module
import org.koin.dsl.module

val mockTestRepo: Module = module {
    factory { MockTestAPIService() }
}