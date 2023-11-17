package com.example.move.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkResponse (
    @SerializedName("code")
    var code: Int,
    @SerializedName("error")
    var error: NetworkError
)