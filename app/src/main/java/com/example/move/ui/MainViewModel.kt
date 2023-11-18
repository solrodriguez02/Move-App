package com.example.move.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.data.DataSourceException
import com.example.move.data.model.Error
import com.example.move.data.repository.ReviewRepository
import com.example.move.data.repository.RoutineRepository
import com.example.move.data.repository.UserRepository
import com.example.move.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val routineRepository: RoutineRepository,
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set


    fun login(username: String, password: String) = runOnViewModelScope(
        { userRepository.login(username, password) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun logout() = runOnViewModelScope(
        { userRepository.logout() },
        { state, _ ->
            state.copy(
                isAuthenticated = false,
                currentUser = null,
            )
        }
    )

    fun getCurrentUser() = runOnViewModelScope(
        { userRepository.getCurrentUser(uiState.currentUser == null) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun changeMode() {
        sessionManager.saveMode(!sessionManager.loadMode())
        uiState = uiState.copy(listMode = sessionManager.loadMode())
    }

    fun setIsListMode() {
        uiState = uiState.copy(listMode = sessionManager.loadMode())
    }

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