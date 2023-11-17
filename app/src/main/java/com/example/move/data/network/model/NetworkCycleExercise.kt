package com.example.move.data.network.model

import com.example.move.data.model.CycleExercise
import com.google.gson.annotations.SerializedName

data class NetworkCycleExercise (

    @SerializedName("exercise")
    var exercise: NetworkExercise,

    @SerializedName("order")
    var order: Int,

    @SerializedName("repetitions")
    var repetitions: Int,

    @SerializedName("metadata")
    var metadata: String ? = null

){
    fun asModel(): CycleExercise{
        return CycleExercise(
            exercise = exercise.asModel(),
            order = order,
            repetitions = repetitions,
            metadata = metadata
        )
    }
}