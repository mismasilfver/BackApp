package com.backpain.exercise.domain.model

data class ExerciseSet(
    val id: String,
    val name: String,
    val description: String,
    val iconResourceId: Int,
    val exerciseIds: List<String>,
    val estimatedDurationMinutes: Int
)
