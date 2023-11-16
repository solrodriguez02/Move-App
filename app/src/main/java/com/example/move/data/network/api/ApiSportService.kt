package com.example.move.data.network.api

import com.example.move.data.network.model.NetworkPagedContent
import com.example.move.data.network.model.NetworkSport
import retrofit2.Response
import retrofit2.http.*

interface ApiSportService {

    @GET("sports")
    suspend fun getSports(@Query("size") size: Int = 50): Response<NetworkPagedContent<NetworkSport>>

    @POST("sports")
    suspend fun addSport(@Body sport: NetworkSport): Response<NetworkSport>

    @GET("sports/{sportId}")
    suspend fun getSport(@Path("sportId") sportId: Int): Response<NetworkSport>

    @PUT("sports/{sportId}")
    suspend fun modifySport(
        @Path("sportId") sportId: Int,
        @Body sport: NetworkSport
    ): Response<NetworkSport>

    @DELETE("sports/{sportId}")
    suspend fun deleteSport(@Path("sportId") sportId: Int): Response<Unit>
}