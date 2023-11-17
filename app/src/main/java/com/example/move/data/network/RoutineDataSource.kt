package com.example.move.data.network

import com.example.move.data.network.api.ApiRoutineService
import com.example.move.data.network.model.NetworkCycleExercise
import com.example.move.data.network.model.NetworkPagedContent
import com.example.move.data.network.model.NetworkResponse
import com.example.move.data.network.model.NetworkRoutine
import com.example.move.data.network.model.NetworkRoutineCycle
import retrofit2.Response

data class RoutineDataSource (
    private val apiRoutineService: ApiRoutineService
) : RemoteDataSource(){
    suspend fun getRoutines(): NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            apiRoutineService.getAllRoutines()
        }
    }

    suspend fun getRoutine(routineId: Int): NetworkRoutine {
        return handleApiResponse {
            apiRoutineService.getRoutineById(routineId)
        }
    }

    suspend fun getRoutineCycles(routineId: Int): NetworkPagedContent<NetworkRoutineCycle> {
        return handleApiResponse {
            apiRoutineService.getRoutineCycles(routineId)
        }
    }

    suspend fun getRoutineCycleExercises(cycleId: Int): NetworkPagedContent<NetworkCycleExercise> {
        return handleApiResponse {
            apiRoutineService.getRoutineCycleExercises(cycleId)
        }
    }

    suspend fun getFavouriteRoutines(): NetworkPagedContent<NetworkRoutine>{
        return handleApiResponse {
            apiRoutineService.getFavouriteRoutines()
        }
    }

    suspend fun addRoutineToFavourites(routineId: Int) : NetworkResponse{
        return handleApiResponse {
            apiRoutineService.addRoutineToFavourites(routineId)
        }
    }

    suspend fun removeRoutineFromFavourites(routineId: Int) : NetworkResponse{
        return handleApiResponse {
            apiRoutineService.removeRoutineFromFavourites(routineId)
        }
    }

}

