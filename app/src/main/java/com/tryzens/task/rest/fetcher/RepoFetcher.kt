package com.tryzens.task.rest.fetcher

import android.content.Context
import com.tryzens.task.rest.*
import com.tryzens.task.rest.api.APIs
import com.tryzens.task.rest.data.RepositoriesData

class RepoFetcher(private val api: APIs, private val context: Context) : BaseFetcher {
    override suspend fun getAllRepositories(): NetworkResponseHandler<List<RepositoriesData>> {
        return if (isConnectionAvailable(context)) {
            try {
                val response = api.getRepositories()
                if (response.isSuccessful) {
                    handleSuccess(response)
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                NetworkResponseHandler.Error(e)
            }
        } else {
            noNetworkConnectivityError()
        }
    }
}