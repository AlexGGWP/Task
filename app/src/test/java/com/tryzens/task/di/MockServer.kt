package com.tryzens.task.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.core.module.Module
import org.koin.dsl.module

val MockWebTestServerInstrumentationTest: Module = module {
    factory {
        MockWebServer()
    }
}