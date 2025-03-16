package com.example.trainme.domain.usecases

import com.example.trainme.domain.repository.ILessonRepository
import com.example.trainme.util.Resource

class RegisterForLessonUseCase(
    private val repository: ILessonRepository
) {
    suspend operator fun invoke(instanceId: Int, userId: Int): Resource<Int> {
        return repository.registerForLesson(instanceId, userId)
    }
}