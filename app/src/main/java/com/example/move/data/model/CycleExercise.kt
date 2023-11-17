package com.example.move.data.model

import com.example.move.data.network.model.NetworkExercise
import com.google.gson.annotations.SerializedName

class CycleExercise (
    var exercise: ExerciseDetail,
    var order: Int,
    var repetitions: Int,
    var metadata: String ? = null
)