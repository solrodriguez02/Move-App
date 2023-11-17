package com.example.move.data.model

class Cycle (
    var id: Int,
    var name: String ? = null,
    var detail: String ? = null,
    var type: String ? = null,
    var order: Int,
    var repetitions: Int,
    var metadata: String ? = null
)