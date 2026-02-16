package com.backpain.exercise.domain.repository

import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.model.ExerciseSet
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExerciseSets(): Flow<List<ExerciseSet>>
    suspend fun getExercisesBySet(setId: String): Flow<List<Exercise>>
    suspend fun getExerciseById(exerciseId: String): Flow<Exercise?>
    suspend fun markExerciseCompleted(exerciseId: String)
    suspend fun resetExerciseCompletion(exerciseId: String)
}
