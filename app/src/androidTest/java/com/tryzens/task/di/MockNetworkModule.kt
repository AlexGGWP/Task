package com.tryzens.task.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tryzens.task.rest.api.APIs
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun networkForTestingModule(api: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(api)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
    factory { get<Retrofit>().create(APIs::class.java) }
}