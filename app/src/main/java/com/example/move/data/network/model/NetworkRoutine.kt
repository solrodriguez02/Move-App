package com.example.move.data.network.model

import com.example.move.data.model.Cycle
import com.example.move.data.model.CycleExercise
import com.example.move.data.model.Filter
import com.example.move.data.model.MetadataRoutine
import com.example.move.data.model.RoutineDetail
import com.example.move.data.model.RoutinePreview
import com.example.move.data.model.User
import com.google.gson.annotations.SerializedName

data class NetworkRoutine (

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("detail")
    var detail: String,

    @SerializedName("isPublic")
    var isPublic: Boolean,

    @SerializedName("difficulty")
    var difficulty: String,

    @SerializedName("score")
    var score: Int,

    @SerializedName("metadata")
    var metadata: NetworkMetadataRoutine? = NetworkMetadataRoutine(NetworkFilters("no details", emptyList(), "no details", emptyList()))

){

    fun asModelPreview(): RoutinePreview {
        return RoutinePreview(
            id = id,
            name = name,
            detail = detail,
            difficulty= difficulty,
            score = score,
            metadata = metadata?.asModel() ?: MetadataRoutine(Filter("unknown", emptyList(), "no details", emptyList()))
        )
    }

    fun asModelDetailed(): RoutineDetail {
        return RoutineDetail(
            id = id,
            name = name,
            detail = detail,
            difficulty= difficulty,
            score = score,
            isFavourite = null,
            cycles = emptyMap<Cycle, List<CycleExercise>>().toMutableMap(),
            metadata = metadata?.asModel() ?: MetadataRoutine(Filter("unknown", emptyList(), "no details", emptyList()))
            )
    }

}
