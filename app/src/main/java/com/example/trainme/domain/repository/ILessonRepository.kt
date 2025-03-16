// com/example/trainme/domain/repository/ILessonRepository.kt
package com.example.trainme.domain.repository

import com.example.trainme.data.models.Lesson
import com.example.trainme.util.Resource
import kotlinx.coroutines.flow.Flow

interface ILessonRepository {
    fun getUpcomingLessons(): Flow<Resource<List<Lesson>>>
    suspend fun registerForLesson(instanceId: Int, userId: Int): Resource<Int>
}