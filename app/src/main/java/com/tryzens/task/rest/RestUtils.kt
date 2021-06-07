package com.tryzens.task.rest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.GsonBuilder
import com.tryzens.task.rest.api.APIError
import retrofit2.Response
import java.io.IOException

fun isConnectionAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.run {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    }
    return result
}

fun noNetworkConnectivityError(): NetworkResponseHandler.Error {
    return NetworkResponseHandler.Error(Exception("Network is not available"))
}

fun <T : Any> handleApiError(resp: Response<T>): NetworkResponseHandler.Error {
    val error = parseError(resp)
    return NetworkResponseHandler.Error(Exception(error.message))
}

fun <T : Any> handleSuccess(response: Response<T>): NetworkResponseHandler.Success<T> {
    response.body()?.let {
        return NetworkResponseHandler.Success(it)
    }
    return null!!
}

private fun parseError(response: Response<*>): APIError {
    val gson = GsonBuilder().create()
    val error: APIError

    try {
        error = gson.fromJson(response.errorBody()?.string(), APIError::class.java)
    } catch (e: IOException) {
        e.message?.let { Log.d("HelperClass", it) }
        return APIError()
    }
    return error
}