package com.example.move.data.network.model

import com.example.move.data.model.ExerciseDetail
import com.google.gson.annotations.SerializedName

data class NetworkExercise (

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
){
    fun asModel(): ExerciseDetail{
        return ExerciseDetail(
            id= id,
            name = name,
            detail = detail,
            type = type,
            order = order
        )
    }
}