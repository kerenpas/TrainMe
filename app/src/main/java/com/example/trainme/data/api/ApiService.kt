package com.example.trainme.data.api

import com.example.trainme.data.models.ApiResponse
import com.example.trainme.data.models.EmptyResponse
import com.example.trainme.data.models.LoginRequest
import com.example.trainme.data.models.UpcomingLessonsResponse
import com.example.trainme.data.models.UserRegistrationRequest
import com.example.trainme.data.models.UserResponse
import com.example.trainme.data.models.UserUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/register")
    suspend fun registerUser(@Body user: UserRegistrationRequest): ApiResponse<UserResponse>

    @POST("api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): ApiResponse<UserResponse>

    @GET("api/users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: Int): ApiResponse<UserResponse>

    @PUT("api/users/{userId}")
    suspend fun updateUserDetails(
        @Path("userId") userId: Int,
        @Body userUpdateRequest: UserUpdateRequest
    ): ApiResponse<EmptyResponse>
    
    @GET("api/lessons/upcoming")
    suspend fun getUpcomingLessons(): ApiResponse<UpcomingLessonsResponse>
    
    @POST("api/lessons/{instanceId}/register")
    suspend fun registerForLesson(
        @Path("instanceId") instanceId: Int,
        @Body request: LessonRegistrationRequest
    ): ApiResponse<LessonRegistrationResponse>
}

data class LessonRegistrationRequest(
    val user_id: Int
)

data class LessonRegistrationResponse(
    val registration_id: Int,
    val message: String
)
