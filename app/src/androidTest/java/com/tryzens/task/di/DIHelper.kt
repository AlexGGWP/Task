package com.tryzens.task.di

import com.tryzens.task.rest.MockWebServerInstrumentationTest
import org.koin.core.module.Module

fun generateTestAppComponent(api: String): List<Module> = listOf(
    networkForTestingModule(api),
    mockRepo,
    MockWebServerInstrumentationTest
)