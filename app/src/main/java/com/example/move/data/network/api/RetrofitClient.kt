package com.example.move.data.network.api

import android.content.Context
import com.example.move.BuildConfig
//import com.example.move.api.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitClient {

    @Volatile
    private var instance: Retrofit? = null

    private fun getInstance(context: Context): Retrofit =
        instance ?: synchronized(this) {
            instance ?: buildRetrofit(context).also { instance = it }
        }

    private fun buildRetrofit(context: Context): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, ApiDateTypeAdapter())
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    fun getApiUserService(context: Context): ApiUserService {
        return getInstance(context).create(ApiUserService::class.java)
    }

    fun getApiRoutineService(context: Context): ApiRoutineService {
        return getInstance(context).create(ApiRoutineService::class.java)
    }

    fun getApiReviewService(context: Context): ApiReviewService {
        return getInstance(context).create(ApiReviewService::class.java)
    }
}
