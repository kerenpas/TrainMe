package com.example.trainme.domain.usecases

import com.example.trainme.data.models.Lesson
import com.example.trainme.domain.repository.ILessonRepository
import com.example.trainme.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUpcomingLessonsUseCase(
    private val repository: ILessonRepository
) {
    operator fun invoke(): Flow<Resource<List<Lesson>>> {
        return repository.getUpcomingLessons()
    }
}