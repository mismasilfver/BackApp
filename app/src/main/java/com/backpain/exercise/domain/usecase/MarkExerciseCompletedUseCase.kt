package com.backpain.exercise.domain.usecase

import com.backpain.exercise.domain.repository.ExerciseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarkExerciseCompletedUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(exerciseId: String) {
        repository.markExerciseCompleted(exerciseId)
    }
}
