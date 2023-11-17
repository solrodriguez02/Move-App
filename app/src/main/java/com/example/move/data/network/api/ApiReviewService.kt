package com.example.move.data.network.api

import com.example.move.data.network.model.NetworkReview
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiReviewService {

    @POST("reviews/{routineId}")
    suspend fun addReview(
        @Path("routineId") routineId: Int,
        @Body review: NetworkReview
    ): Response<NetworkReview>

}