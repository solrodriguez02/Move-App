package com.example.move.data.model

class RoutineDetail (
    var id: Int,
    var name: String,
    var detail: String? = "No description",
    var score: Int,
    var difficulty: String,
    var cycles: MutableMap<Cycle, List<CycleExercise>>,
    var isFavourite: Boolean ? = false
)