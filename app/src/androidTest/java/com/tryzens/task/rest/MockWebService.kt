package com.tryzens.task.rest

import okhttp3.mockwebserver.MockWebServer
import org.koin.core.module.Module
import org.koin.dsl.module

val MockWebServerInstrumentationTest: Module = module {
    factory {
        MockWebServer()
    }
}