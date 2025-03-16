// com/example/trainme/presentation/viewmodel/UpcomingLessonsViewModel.kt
package com.example.trainme.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainme.data.models.Lesson
import com.example.trainme.domain.usecases.GetUpcomingLessonsUseCase
import com.example.trainme.domain.usecases.RegisterForLessonUseCase
import com.example.trainme.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UpcomingLessonsViewModel(
    private val getUpcomingLessonsUseCase: GetUpcomingLessonsUseCase,
    private val registerForLessonUseCase: RegisterForLessonUseCase
) : ViewModel() {

    // State for upcoming lessons
    private val _lessonsState = MutableStateFlow(LessonsState())
    val lessonsState: StateFlow<LessonsState> = _lessonsState.asStateFlow()

    // Events for user feedback
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    // Currently selected lesson
    private val _selectedLesson = MutableStateFlow<Lesson?>(null)
    val selectedLesson: StateFlow<Lesson?> = _selectedLesson.asStateFlow()

    // Booking in progress state
    private val _bookingInProgress = MutableStateFlow(false)
    val bookingInProgress: StateFlow<Boolean> = _bookingInProgress.asStateFlow()

    init {
        loadUpcomingLessons()
    }

    fun loadUpcomingLessons() {
        getUpcomingLessonsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _lessonsState.value = LessonsState(
                        lessons = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _lessonsState.value = LessonsState(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: "An unexpected error occurred"))
                }
                is Resource.Loading -> {
                    _lessonsState.value = LessonsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectLesson(lesson: Lesson) {
        _selectedLesson.value = lesson
    }

    fun clearSelectedLesson() {
        _selectedLesson.value = null
    }

    fun registerForLesson(userId: Int) {
        val lesson = _selectedLesson.value
        if (lesson == null) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar("No lesson selected"))
            }
            return
        }

        if (lesson.isFull) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowSnackbar("This class is full"))
            }
            return
        }

        _bookingInProgress.value = true

        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowRegistrationInProgress)

            val result = registerForLessonUseCase(lesson.instanceId, userId)

            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.RegistrationSuccess)

                    // Update the lessons list to reflect the booking
                    val updatedLessons = _lessonsState.value.lessons.map {
                        if (it.instanceId == lesson.instanceId) {
                            // Create a copy with incremented participant count
                            it.copy(
                                currentParticipants = it.currentParticipants + 1,
                                isFull = (it.currentParticipants + 1) >= it.maxParticipants
                            )
                        } else {
                            it
                        }
                    }

                    _lessonsState.value = _lessonsState.value.copy(lessons = updatedLessons)
                    _bookingInProgress.value = false
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: "Registration failed"))
                    _bookingInProgress.value = false
                }
                else -> {
                    _bookingInProgress.value = false
                }
            }
        }
    }

    data class LessonsState(
        val lessons: List<Lesson> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = ""
    )

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object ShowRegistrationInProgress : UiEvent()
        object RegistrationSuccess : UiEvent()
    }
}