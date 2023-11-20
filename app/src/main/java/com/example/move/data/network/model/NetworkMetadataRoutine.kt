package com.example.move.data.network.model

import com.example.move.data.model.Cycle
import com.example.move.data.model.Filter
import com.example.move.data.model.MetadataRoutine
import com.google.gson.annotations.SerializedName

class NetworkMetadataRoutine (
    @SerializedName("filters")
    var filters: NetworkFilters?
){
    fun asModel(): MetadataRoutine {
        return MetadataRoutine(
            filters = filters?.asModel() ?: Filter("unknown", emptyList(), "no details", emptyList())
        )
    }
}