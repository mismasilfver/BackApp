package com.backpain.exercise.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_sets")
data class ExerciseSetEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val iconResourceId: Int,
    val exerciseIds: List<String>,
    val estimatedDurationMinutes: Int
)
