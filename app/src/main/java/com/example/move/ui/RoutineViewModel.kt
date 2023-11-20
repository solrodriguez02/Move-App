package com.example.move.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.data.DataSourceException
import com.example.move.data.model.Error
import com.example.move.data.model.RoutinePreview
import com.example.move.data.repository.RoutineRepository
import com.example.move.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.move.R

class RoutineViewModel(
    sessionManager: SessionManager,
    private val routineRepository: RoutineRepository
) : ViewModel() {
        var uiState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
            private set

        fun getRoutinePreviews(orderBy :String = "date", direction :String = "asc") = runOnViewModelScope(
            { routineRepository.getRoutinePreviews(refresh = true, orderBy = orderBy, direction = direction) },
            { state, response -> state.copy(routinePreviews = response) }
        )

        fun getFilteredRoutinePreviews(
            filtersSelected :List<SelectedFilter>,
            isOrderedByDate :Boolean,
            direction :String = "asc"
        ) {
            if(isOrderedByDate) {
                getRoutinePreviews(orderBy = "date", direction = direction)
            }

            /*
            var selectedRoutines = uiState.routinePreviews
            var toAdd = true

            for(routine in uiState.routinePreviews!!) {
                toAdd = true
                for(filter in filtersSelected) {
                    if(filter.category == "difficulty") {
                        if(filter.filter != routine.metadata.difficulty) toAdd = false
                    } else if(filter.category == "elements") {

                    } else if(filter.category == "spaceRequired") {
                        if(filter.filter != routine.metadata.difficulty) toAdd = false
                    } else if(filter.category == "approach") {

                    }
                }
            }


            uiState = uiState.copy(routinePreviews = selectedRoutines)

             */
        }

        fun getRoutine(routineId: Int) = runOnViewModelScope(
            { routineRepository.getRoutine(routineId)},
            {state, response -> state.copy(currentRoutine = response )}
        )

        fun addRoutineToFavourites(routineId: Int) = runOnViewModelScope(
            { routineRepository.addRoutineToFavourites(routineId)},
            {state, response -> state.copy(isCurrentRoutineFav = response)}
        )

        fun removeRoutineToFavourites(routineId: Int) = runOnViewModelScope(
            { routineRepository.removeRoutineFromFavourites(routineId)},
            {state, response -> state.copy(isCurrentRoutineFav = response)}
        )

        fun isRoutineInFavourites(routineId: Int) :Boolean {
            var routineInFavourites = false
            viewModelScope.launch {
                runCatching {
                    routineInFavourites = routineRepository.isRoutineInFavourites(routineId)
                }
            }
            return routineInFavourites
        }

        fun getFavouriteRoutines() = runOnViewModelScope(
            { routineRepository.getFavouriteRoutines() },
            { state, response -> state.copy(favRoutinePreviews = response) }
        )

        private fun <R> runOnViewModelScope(
            block: suspend () -> R,
            updateState: (MainUiState, R) -> MainUiState
        ): Job = viewModelScope.launch {
            uiState = uiState.copy(isFetching = true, error = null)
            runCatching {
                block()
            }.onSuccess { response ->
                uiState = updateState(uiState, response).copy(isFetching = false)
            }.onFailure { e ->
                uiState = uiState.copy(isFetching = false, error = handleError(e))
            }
        }

        private fun handleError(e: Throwable): Error {
            return if (e is DataSourceException) {
                Error(e.code, e.message ?: "", e.details)
            } else {
                Error(null, e.message ?: "", null)
            }
        }
}