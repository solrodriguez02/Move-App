package com.example.move.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.data.DataSourceException
import com.example.move.data.model.Error
import com.example.move.data.repository.RoutineRepository
import com.example.move.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RoutineViewModel(
    sessionManager: SessionManager,
    private val routineRepository: RoutineRepository
) : ViewModel() {
        var uiState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
            private set

        fun getRoutinePreviews() = runOnViewModelScope(
            { routineRepository.getRoutinePreviews(refresh = true) },
            { state, response -> state.copy(routinePreviews = response) }
        )

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