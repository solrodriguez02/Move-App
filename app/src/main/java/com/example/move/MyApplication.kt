package com.example.move

import android.app.Application
import com.example.move.data.model.Review
import com.example.move.data.network.ReviewRemoteDataSource
import com.example.move.data.network.RoutineDataSource
import com.example.move.data.network.SportRemoteDataSource
import com.example.move.data.network.UserRemoteDataSource
import com.example.move.data.network.api.RetrofitClient
import com.example.move.data.repository.ReviewRepository
import com.example.move.data.repository.RoutineRepository
import com.example.move.data.repository.SportRepository
import com.example.move.data.repository.UserRepository
import com.example.move.util.SessionManager

class  MyApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    private val routineRemoteDataSource: RoutineDataSource
        get() = RoutineDataSource(RetrofitClient.getApiRoutineService(this))

    private val reviewRemoteDataSource: ReviewRemoteDataSource
        get() = ReviewRemoteDataSource(RetrofitClient.getApiReviewService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val routineRepository: RoutineRepository
        get() = RoutineRepository(routineRemoteDataSource)

    val reviewRepository: ReviewRepository
        get() = ReviewRepository(reviewRemoteDataSource)
}