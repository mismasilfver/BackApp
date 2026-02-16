package com.backpain.exercise.domain.usecase

import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetExerciseDetailUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(exerciseId: String): Flow<Exercise?> {
        return repository.getExerciseById(exerciseId)
    }
}
