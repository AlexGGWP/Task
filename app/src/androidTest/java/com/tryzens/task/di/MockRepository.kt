package com.tryzens.task.di

import com.tryzens.task.rest.MockAPIService
import org.koin.dsl.module

val mockRepo = module {
    factory { MockAPIService() }
}