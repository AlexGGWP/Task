package com.tryzens.task.rest

import com.tryzens.task.rest.api.APIs
import com.tryzens.task.rest.data.RepositoriesData
import com.tryzens.task.rest.fetcher.BaseFetcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MockAPIService : BaseFetcher, KoinComponent {

    val api: APIs by inject()
    override suspend fun getAllRepositories(): NetworkResponseHandler<List<RepositoriesData>> {
        return try {
            val response = api.getRepositories()
            if (response.isSuccessful) {
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            NetworkResponseHandler.Error(e)
        }
    }
}