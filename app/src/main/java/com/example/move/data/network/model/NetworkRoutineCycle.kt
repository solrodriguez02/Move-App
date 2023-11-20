package com.example.move.data.network.model

import com.example.move.data.model.Cycle
import com.example.move.data.model.RoutinePreview
import com.google.gson.annotations.SerializedName
import retrofit2.http.Path

data class NetworkRoutineCycle (

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String ? = null,

    @SerializedName("detail")
    var detail: String ? = null,

    @SerializedName("type")
    var type: String ? = null,

    @SerializedName("order")
    var order: Int,

    @SerializedName("repetitions")
    var repetitions: Int,

) {
    fun asModel(): Cycle {
        return Cycle(
            id = id,
            name = name,
            detail = detail,
            type = type,
            order = order,
            repetitions = repetitions,
        )
    }
}