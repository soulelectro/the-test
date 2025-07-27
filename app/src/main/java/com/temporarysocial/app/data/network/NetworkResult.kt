
package com.temporarysocial.app.data.network

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Exception<T>(val e: Throwable) : NetworkResult<T>(message = e.localizedMessage)
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (throwable: Throwable) {
        when (throwable) {
            is retrofit2.HttpException -> {
                NetworkResult.Error("HTTP ${throwable.code()}: ${throwable.message()}")
            }
            is java.io.IOException -> {
                NetworkResult.Error("Network error: ${throwable.message}")
            }
            else -> {
                NetworkResult.Exception(throwable)
            }
        }
    }
}
