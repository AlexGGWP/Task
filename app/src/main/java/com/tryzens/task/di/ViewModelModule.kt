package com.tryzens.task.di

import com.tryzens.task.viewmodels.RepositoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RepositoryViewModel(fetcher = get())
    }
}