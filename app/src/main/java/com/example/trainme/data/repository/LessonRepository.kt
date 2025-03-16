// com/example/trainme/data/repository/LessonRepository.kt
package com.example.trainme.data.repository

import com.example.trainme.data.api.ApiService
import com.example.trainme.data.api.LessonRegistrationRequest
import com.example.trainme.data.models.Lesson
import com.example.trainme.data.models.toDomain
import com.example.trainme.domain.repository.ILessonRepository
import com.example.trainme.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LessonRepository(
    private val apiService: ApiService
) : ILessonRepository {

    override fun getUpcomingLessons(): Flow<Resource<List<Lesson>>> = flow {
        emit(Resource.Loading())

        try {
            val response = apiService.getUpcomingLessons()
            if (response.isSuccess && response.data != null) {
                val lessons = response.data.lessons.map { it.toDomain() }
                emit(Resource.Success(lessons))
            } else {
                emit(Resource.Error(response.message ?: "Unknown error"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.message}"))
        }
    }

    override suspend fun registerForLesson(instanceId: Int, userId: Int): Resource<Int> {
        return try {
            val response = apiService.registerForLesson(
                instanceId = instanceId,
                request = LessonRegistrationRequest(user_id = userId)
            )

            if (response.isSuccess && response.data != null) {
                Resource.Success(response.data.registration_id)
            } else {
                Resource.Error(response.message ?: "Registration failed")
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred: ${e.message}")
        }
    }
}

