package com.tryzens.task.rest

import com.tryzens.task.rest.data.RepositoriesData

sealed class NetworkResponseHandler<T> {
    data class Success<T>(val successData: T) : NetworkResponseHandler<T>()
    class Error(
        val exception: java.lang.Exception,
        val message: String = "A network error has occurred!"
    ) : NetworkResponseHandler<List<RepositoriesData>>()
}
