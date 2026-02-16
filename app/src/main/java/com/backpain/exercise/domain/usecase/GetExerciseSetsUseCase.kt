package com.backpain.exercise.domain.usecase

import com.backpain.exercise.domain.model.ExerciseSet
import com.backpain.exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetExerciseSetsUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<List<ExerciseSet>> {
        return repository.getExerciseSets()
    }
}
