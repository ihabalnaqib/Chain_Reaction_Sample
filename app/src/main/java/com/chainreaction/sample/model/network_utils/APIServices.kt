package com.chainreaction.sample.model.network_utils

import com.chainreaction.sample.model.model.InventoryModel
import com.chainreaction.sample.model.network_utils.remote.ErrorResponseBody
import com.chainreaction.sample.model.network_utils.remote.NetworkResponse
import retrofit2.http.*

typealias GenericResponse<S> = NetworkResponse<S, ErrorResponseBody>

interface APIServices {

    @GET(INVENTORIES)
    suspend fun getInventories(
    ): GenericResponse<List<InventoryModel>>

}

private const val INVENTORIES = "inventory"
