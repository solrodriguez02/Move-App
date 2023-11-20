package com.example.move.data.network.model

import com.example.move.data.model.Cycle
import com.example.move.data.model.Filter
import com.example.move.data.model.MetadataRoutine
import com.google.gson.annotations.SerializedName

class NetworkFilters (
    @SerializedName("difficulty")
    var difficulty: String,
    @SerializedName("elements")
    var elements: List<String>,
    @SerializedName("requiredSpace")
    var requiredSpace: String,
    @SerializedName("approach")
    var approach: List<String>
){
    fun asModel(): Filter {
        return Filter(
            difficulty = difficulty,
            elements = elements,
            requiredSpace = requiredSpace,
            approach = approach
        )
    }
}