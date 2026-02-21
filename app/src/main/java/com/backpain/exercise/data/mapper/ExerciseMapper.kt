package com.backpain.exercise.data.mapper

import com.backpain.exercise.data.database.entity.ExerciseEntity
import com.backpain.exercise.domain.model.DifficultyLevel
import com.backpain.exercise.domain.model.Exercise

// Extension functions for mapping between domain and data layers

fun ExerciseEntity.toDomainModel(): Exercise {
    return Exercise(
        id = id,
        title = title,
        description = description,
        illustrationResourceId = illustrationResourceId,
        youtubeVideoId = youtubeVideoId,
        durationSeconds = durationSeconds,
        difficultyLevel = DifficultyLevel.valueOf(difficultyLevel),
        instructions = instructions,
        isCompleted = isCompleted
    )
}

fun Exercise.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        title = title,
        description = description,
        illustrationResourceId = illustrationResourceId,
        youtubeVideoId = youtubeVideoId,
        durationSeconds = durationSeconds,
        difficultyLevel = difficultyLevel.name,
        instructions = instructions,
        isCompleted = isCompleted
    )
}
