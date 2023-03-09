package com.chainreaction.sample.model.network_utils.remote

import com.chainreaction.sample.model.utils.BaseResponse
import java.io.IOException
import java.util.concurrent.TimeoutException

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: BaseResponse.Status, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()


    /**
     * Timeout error
     */
    data class TimeoutError(val error: TimeoutException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}