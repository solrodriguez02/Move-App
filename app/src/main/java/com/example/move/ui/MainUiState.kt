package com.example.move.ui

import com.example.move.data.model.Error
import com.example.move.data.model.Sport
import com.example.move.data.model.User

data class MainUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val sports: List<Sport>? = null,
    val currentSport: Sport? = null,
    val error: Error? = null,
    val listMode: Boolean = false,
)

val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val MainUiState.canGetAllSports: Boolean get() = isAuthenticated
val MainUiState.canGetCurrentSport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canAddSport: Boolean get() = isAuthenticated && currentSport == null
val MainUiState.canModifySport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canDeleteSport: Boolean get() = canModifySport
