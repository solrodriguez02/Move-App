package com.example.move.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

        fun getPersonalRoutinePreviews() = runOnViewModelScope(
            { routineRepository.getPersonalRoutines() },
            { state, response -> state.copy(personalRoutinePreviews = response) }
        )

        fun getFilteredRoutinePreviews(
            filtersSelected :List<SelectedFilter>,
            isOrderedByDate :Boolean,
            direction :String = "asc"
        ) {
            if(isOrderedByDate) {
                getRoutinePreviews(orderBy = "date", direction = direction)
            }
            applyFilters(filtersSelected)
        }

        private fun applyFilters(filtersSelected :List<SelectedFilter>) {
            var selectedRoutines :MutableList<RoutinePreview> = mutableListOf()
            var toAdd :Boolean

            for(routine in uiState.routinePreviews!!) {
                toAdd = true
                for(filter in filtersSelected) {
                    if(filter.category == "difficulty") {
                        if(filter.filter != routine.metadata.filters.difficulty) toAdd = false
                    }
                    else if(filter.category == "elements") {
                        var found = false
                        for(elementFilter in routine.metadata.filters.elements) {
                            if(filter.filter == elementFilter) {
                                found = true
                            }
                        }
                        if(!found) toAdd = false
                    }
                    else if(filter.category == "requiredSpace") {
                        if(filter.filter != routine.metadata.filters.requiredSpace) toAdd = false
                    }
                    else if(filter.category == "approach") {
                        var found = false
                        for(approachFilter in routine.metadata.filters.approach) {
                            if(filter.filter == approachFilter) {
                                found = true
                            }
                        }
                        if(!found) toAdd = false
                    } else if(filter.category == "score") {
                        when(filter.filter) {
                            "Bad" -> if(routine.score > 2) toAdd = false
                            "Good" -> if(routine.score <= 2 || routine.score > 4 ) toAdd = false
                            "Excellent" -> if(routine.score < 4) toAdd = false
                        }
                    }
                }
                if(toAdd) {
                    selectedRoutines.add(routine)
                }
            }
            uiState = uiState.copy(routinePreviews = selectedRoutines)
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