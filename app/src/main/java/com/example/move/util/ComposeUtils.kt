package com.example.move.util

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import com.example.move.MyApplication

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as MyApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
    val reviewRepository = application.reviewRepository
    return ViewModelFactory(
        sessionManager,
        userRepository,
        reviewRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}