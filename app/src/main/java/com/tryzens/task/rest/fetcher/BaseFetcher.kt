package com.tryzens.task.rest.fetcher

import com.tryzens.task.rest.NetworkResponseHandler
import com.tryzens.task.rest.data.RepositoriesData

interface BaseFetcher {
    suspend fun getAllRepositories(): NetworkResponseHandler<List<RepositoriesData>>
}