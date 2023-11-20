package com.example.move.data.model

class CycleExercise(
    var exercise: ExerciseDetail?,
    var order: Int,
    var duration: Int,
    var repetitions: Int,
    var metadata: String ? = null
)