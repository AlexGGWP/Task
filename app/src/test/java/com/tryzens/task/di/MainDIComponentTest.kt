package com.tryzens.task.di

import org.koin.core.module.Module

fun configureTestAppComponent(baseApi: String): List<Module> = listOf(
    mockNetworkForTestingModule(baseApi),
    viewModelModule,
    MockWebTestServerInstrumentationTest,
    mockTestRepo
)