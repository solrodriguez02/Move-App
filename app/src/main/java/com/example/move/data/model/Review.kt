package com.example.move.data.model

import com.example.move.data.network.model.NetworkReview

class Review (
    var score: Int,
    var review: String = ""
) {
    fun asNetworkModel(): NetworkReview {
        return NetworkReview(
            score = score,
            review = review
        )
    }
}