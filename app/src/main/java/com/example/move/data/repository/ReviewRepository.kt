package com.example.move.data.repository

import com.example.move.data.model.Review
import com.example.move.data.network.ReviewRemoteDataSource
import kotlinx.coroutines.sync.Mutex

class ReviewRepository(
    private val remoteDataSource: ReviewRemoteDataSource
) {

    suspend fun addReview(routineId: Int, review: Review): Review {
        return remoteDataSource.addReview(routineId, review.asNetworkModel()).asModel()
    }
}