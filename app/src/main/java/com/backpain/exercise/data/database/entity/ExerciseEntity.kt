package com.backpain.exercise.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val illustrationResourceId: Int,
    val youtubeVideoId: String?,
    val durationSeconds: Int,
    val difficultyLevel: String, // Store as string for Room compatibility
    val instructions: List<String>,
    val isCompleted: Boolean = false
)
