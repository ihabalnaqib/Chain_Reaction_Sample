package com.chainreaction.sample.model.utils


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status")
    val status: Status?,
    @SerializedName("user")
    val user: Any?
) {
    data class Status(
        @SerializedName("errorCode")
        val errorCode: Int?,
        @SerializedName("errorMessage")
        val errorMessage: String?,
        @SerializedName("success")
        val success: Boolean?
    )
}