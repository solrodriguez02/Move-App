package com.example.move.data.network.model

import com.example.move.data.model.Review
import com.google.gson.annotations.SerializedName

data class NetworkReview(

    @SerializedName("score")
    var score: Int,
    @SerializedName("review")
    var review: String = ""

){

    fun asModel(): Review {
        return Review(
            score = score,
            review = review
        )
    }
}