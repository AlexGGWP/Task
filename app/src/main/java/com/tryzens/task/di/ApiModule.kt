package com.tryzens.task.di

import com.tryzens.task.rest.api.APIs
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideRepositoriesApi(retrofit: Retrofit): APIs {
        return retrofit.create(APIs::class.java)
    }
    single { provideRepositoriesApi(get()) }
}