package com.example.move.ui

import com.example.move.data.model.Error
import com.example.move.data.model.RoutineDetail
import com.example.move.data.model.RoutinePreview
import com.example.move.data.model.User

data class MainUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val routinePreviews: List<RoutinePreview>? = null,
    val currentRoutine: RoutineDetail? = null,
    val isCurrentRoutineFav: Boolean = false,
    val favRoutinePreviews: List<RoutinePreview>? = null,
    val personalRoutinePreviews: List<RoutinePreview>? = null,
    val error: Error? = null,
    val listMode: Boolean = false,
    val sound: Boolean = true,
    val exerciseIndex :Int = 0,
    val cycleIndex :Int = 0,
    val currentTime :Long = 0,
    val route :Int = -1,
)

val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val MainUiState.canGetAllRoutines: Boolean get() = isAuthenticated
val MainUiState.canGetCurrentRoutine: Boolean get() = isAuthenticated
val MainUiState.isCurrentRoutineFavourite: Boolean get() = isCurrentRoutineFavourite
val MainUiState.areRoutinesPreviewsReady: Boolean get() = routinePreviews?.isEmpty()== true