package com.example.move.data.network.model

import com.example.move.data.model.Sport
import com.google.gson.annotations.SerializedName

class NetworkSport(

    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String,
    @SerializedName("detail")
    var detail: String? = null
) {

    fun asModel(): Sport {
        return Sport(
            id = id,
            name = name,
            detail = detail
        )
    }
}