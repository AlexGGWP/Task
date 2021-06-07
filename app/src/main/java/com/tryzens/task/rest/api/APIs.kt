package com.tryzens.task.rest.api

import com.tryzens.task.rest.data.RepositoriesData
import retrofit2.Response
import retrofit2.http.GET

/**
 * Used to hold all the possible API's that needs to be dealt with.
 */
interface APIs {
    @GET("repos")
    suspend fun getRepositories(): Response<List<RepositoriesData>>
}