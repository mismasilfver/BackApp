package com.backpain.exercise.domain.model

data class Exercise(
    val id: String,
    val title: String,
    val description: String,
    val illustrationResourceId: Int,
    val youtubeVideoId: String?,
    val durationSeconds: Int,
    val difficultyLevel: DifficultyLevel,
    val instructions: List<String>,
    val isCompleted: Boolean = false
)
