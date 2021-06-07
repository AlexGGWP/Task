package com.tryzens.task.di

import android.content.Context
import com.tryzens.task.rest.api.APIs
import com.tryzens.task.rest.fetcher.BaseFetcher
import com.tryzens.task.rest.fetcher.RepoFetcher
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repoModule = module {

    fun provideRepositories(api: APIs, context: Context): BaseFetcher {
        return RepoFetcher(api, context)
    }
    single { provideRepositories(get(), androidContext()) }

}