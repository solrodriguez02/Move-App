package com.example.move

import android.app.Application
import com.example.move.data.model.Review
import com.example.move.data.network.ReviewRemoteDataSource
import com.example.move.data.network.SportRemoteDataSource
import com.example.move.data.network.UserRemoteDataSource
import com.example.move.data.network.api.RetrofitClient
import com.example.move.data.repository.ReviewRepository
import com.example.move.data.repository.SportRepository
import com.example.move.data.repository.UserRepository
import com.example.move.util.SessionManager

class  MyApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    private val sportRemoteDataSource: SportRemoteDataSource
        get() = SportRemoteDataSource(RetrofitClient.getApiSportService(this))

    private val reviewRemoteDataSource: ReviewRemoteDataSource
        get() = ReviewRemoteDataSource(RetrofitClient.getApiReviewService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val sportRepository: SportRepository
        get() = SportRepository(sportRemoteDataSource)

    val reviewRepository: ReviewRepository
        get() = ReviewRepository(reviewRemoteDataSource)
}