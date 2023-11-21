package com.example.move.data.network.model

import com.example.move.data.model.ExerciseDetail
import com.example.move.data.model.MetadataExercise
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

    @SerializedName("metadata")
    var metadata: NetworkMetadataExercise? = NetworkMetadataExercise("no image", "unknown", emptyList(), emptyList(), "unknown", "unknown")
){
    fun asModel(): ExerciseDetail{
        return ExerciseDetail(
            id= id,
            name = name,
            detail = detail,
            type = type,
            metadata = metadata?.asModel() ?: MetadataExercise("no image", "unknown", emptyList(), emptyList(), "unknown", "unknown")
        )
    }
}