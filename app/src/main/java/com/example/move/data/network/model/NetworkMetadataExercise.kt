package com.example.move.data.network.model

import com.example.move.data.model.MetadataExercise
import com.google.gson.annotations.SerializedName

class NetworkMetadataExercise (
    @SerializedName("image")
    var image: String,
    @SerializedName("difficulty")
    var difficulty: String,
    @SerializedName("muscleGroups")
    var muscleGroups: List<String>,
    @SerializedName("elements")
    var elements: List<String>,
    @SerializedName("space")
    var space: String,
    @SerializedName("creator")
    var creator: String
){
    fun asModel(): MetadataExercise{
        return MetadataExercise(
            image = image,
            difficulty = difficulty,
            muscleGroups = muscleGroups,
            elements = elements,
            space = space,
            creator = creator
        )
    }
}