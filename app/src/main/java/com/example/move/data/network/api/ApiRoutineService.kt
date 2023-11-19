package com.example.move.data.network.api

import com.example.move.data.network.model.NetworkCycleExercise
import com.example.move.data.network.model.NetworkPagedContent
import com.example.move.data.network.model.NetworkResponse
import com.example.move.data.network.model.NetworkRoutine
import com.example.move.data.network.model.NetworkRoutineCycle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRoutineService {
    @GET("routines")
    suspend fun getAllRoutines(@Query("size") size: Int = 100): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines/{routineId}")
    suspend fun getRoutineById(@Path("routineId") routineId: Int): Response<NetworkRoutine>

    @GET("routines/{routineId}/cycles")
    suspend fun getRoutineCycles(@Path("routineId") routineId: Int): Response<NetworkPagedContent<NetworkRoutineCycle>>

    @GET("cycles/{cycleId}/exercises")
    suspend fun getRoutineCycleExercises(@Path("cycleId") cycleId: Int): Response<NetworkPagedContent<NetworkCycleExercise>>

    @GET("favourites")
    suspend fun getFavouriteRoutines(): Response<NetworkPagedContent<NetworkRoutine>>

    @POST("favourites/{routineId}")
    suspend fun addRoutineToFavourites(@Path("routineId") routineId: Int) : Response<NetworkResponse>

    @DELETE("favourites/{routineId}")
    suspend fun removeRoutineFromFavourites(@Path("routineId") routineId: Int) : Response<NetworkResponse>
}