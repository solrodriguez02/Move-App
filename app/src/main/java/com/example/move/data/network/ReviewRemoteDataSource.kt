package com.example.move.data.network

import com.example.move.data.network.api.ApiReviewService
import com.example.move.data.network.model.NetworkReview

class ReviewRemoteDataSource (
    private val apiReviewService: ApiReviewService
) : RemoteDataSource() {

    suspend fun addReview(routineId: Int, review: NetworkReview): NetworkReview {
        return handleApiResponse {
            apiReviewService.addReview(routineId, review)
        }
    }

}