package com.example.move.data.repository

import android.util.Log
import com.example.move.data.model.Cycle
import com.example.move.data.model.CycleExercise
import com.example.move.data.model.RoutineDetail
import com.example.move.data.model.RoutinePreview
import com.example.move.data.model.User
import com.example.move.data.network.RoutineDataSource
import com.example.move.data.network.model.NetworkCycleExercise
import com.example.move.data.network.model.NetworkPagedContent
import com.example.move.data.network.model.NetworkRoutine
import com.example.move.data.network.model.NetworkRoutineCycle
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Map

class RoutineRepository (
    private val remoteDataSource: RoutineDataSource
){
    private var currentRoutine: RoutineDetail? = null
    private var routinePreviews: List<RoutinePreview> = emptyList<RoutinePreview>().toMutableList()

    suspend fun getRoutinePreviews(refresh: Boolean): List<RoutinePreview>{
        if (refresh || routinePreviews.isEmpty() ) {
            val result = remoteDataSource.getRoutines()
            this.routinePreviews = result.content.map { it.asModelPreview() }
        }
        return routinePreviews
    }
    suspend fun getRoutine(routineId: Int): RoutineDetail{
        this.currentRoutine = remoteDataSource.getRoutine(routineId).asModelDetailed()
        this.getRoutineCyclesAndExercises(currentRoutine!!.id)
        this.currentRoutine?.isFavourite = isRoutineInFavourites()
        return currentRoutine as RoutineDetail
    }

    private suspend fun getRoutineCyclesAndExercises(routineId: Int){
        var cycles = remoteDataSource.getRoutineCycles(routineId).content.toMutableList().map { it.asModel() }
        for (i in 0 until cycles.lastIndex){
            this.currentRoutine?.cycles?.put(cycles[i], remoteDataSource.getRoutineCycleExercises(cycles[i].id).content.map { it.asModel() })
        }
    }

    private suspend fun isRoutineInFavourites(): Boolean{
        val routines = remoteDataSource.getFavouriteRoutines().content.map { it.asModelPreview() }
        for (i in 0 until routines.lastIndex){
            if (routines[i].id == this.currentRoutine?.id)
                return true
        }
        return false
    }
    suspend fun addRoutineToFavourites(routineId: Int): Boolean{
        if (currentRoutine?.isFavourite == false){
            remoteDataSource.addRoutineToFavourites(routineId)
            return true
        }
        return false
    }

    suspend fun removeRoutineFromFavourites(routineId: Int):Boolean{

            remoteDataSource.removeRoutineFromFavourites(routineId)
            return false

    }
}