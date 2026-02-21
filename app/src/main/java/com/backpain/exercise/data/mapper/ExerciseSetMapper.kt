package com.backpain.exercise.data.mapper

import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import com.backpain.exercise.domain.model.ExerciseSet

// Extension functions for mapping between domain and data layers

fun ExerciseSetEntity.toDomainModel(): ExerciseSet {
    return ExerciseSet(
        id = id,
        name = name,
        description = description,
        iconResourceId = iconResourceId,
        exerciseIds = exerciseIds,
        estimatedDurationMinutes = estimatedDurationMinutes
    )
}

fun ExerciseSet.toEntity(): ExerciseSetEntity {
    return ExerciseSetEntity(
        id = id,
        name = name,
        description = description,
        iconResourceId = iconResourceId,
        exerciseIds = exerciseIds,
        estimatedDurationMinutes = estimatedDurationMinutes
    )
}
