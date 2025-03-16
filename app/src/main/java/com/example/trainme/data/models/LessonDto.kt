// com/example/trainme/data/models/LessonModels.kt
package com.example.trainme.data.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class UpcomingLessonsResponse(
    val lessons: List<LessonDto>,
    val count: Int
)

data class LessonDto(
    @SerializedName("instance_id") val instanceId: Int,
    @SerializedName("lesson_id") val lessonId: Int,
    val name: String,
    val type: String,
    val date: String,
    @SerializedName("day_name") val dayName: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("duration_minutes") val durationMinutes: Int,
    val location: String,
    val instructor: InstructorDto,
    @SerializedName("max_participants") val maxParticipants: Int,
    @SerializedName("current_participants") val currentParticipants: Int,
    @SerializedName("is_full") val isFull: Boolean,
    val notes: String?,
    val description: String?
)

data class InstructorDto(
    val id: Int,
    val name: String
)

// Domain models
data class Lesson(
    val instanceId: Int,
    val lessonId: Int,
    val name: String,
    val type: String,
    val date: LocalDate,
    val dayName: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val durationMinutes: Int,
    val location: String,
    val instructor: Instructor,
    val maxParticipants: Int,
    val currentParticipants: Int,
    val isFull: Boolean,
    val notes: String?,
    val description: String?
)

data class Instructor(
    val id: Int,
    val name: String
)

// Mappers
fun LessonDto.toDomain(): Lesson {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    
    return Lesson(
        instanceId = instanceId,
        lessonId = lessonId,
        name = name,
        type = type,
        date = LocalDate.parse(date, dateFormatter),
        dayName = dayName,
        startTime = LocalTime.parse(startTime, timeFormatter),
        endTime = LocalTime.parse(endTime, timeFormatter),
        durationMinutes = durationMinutes,
        location = location,
        instructor = instructor.toDomain(),
        maxParticipants = maxParticipants,
        currentParticipants = currentParticipants,
        isFull = isFull,
        notes = notes,
        description = description
    )
}

fun InstructorDto.toDomain(): Instructor = Instructor(id = id, name = name)
