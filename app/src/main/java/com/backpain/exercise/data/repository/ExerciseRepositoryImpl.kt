package com.backpain.exercise.data.repository

import com.backpain.exercise.data.database.AppDatabase
import com.backpain.exercise.data.database.entity.ExerciseEntity
import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import com.backpain.exercise.data.mapper.toDomainModel
import com.backpain.exercise.data.mapper.toEntity
import com.backpain.exercise.domain.model.Exercise
import com.backpain.exercise.domain.model.ExerciseSet
import com.backpain.exercise.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : ExerciseRepository {

    override suspend fun getExerciseSets(): Flow<List<ExerciseSet>> {
        return database.exerciseSetDao()
            .getAllExerciseSets()
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    override suspend fun getExercisesBySet(setId: String): Flow<List<Exercise>> {
        return database.exerciseSetDao()
            .getExerciseSetById(setId)
            .map { exerciseSet ->
                exerciseSet?.let { set ->
                    database.exerciseDao()
                        .getExercisesBySetIds(set.exerciseIds)
                        .first()
                        .map { it.toDomainModel() }
                } ?: emptyList()
            }
    }

    override suspend fun getExerciseById(exerciseId: String): Flow<Exercise?> {
        return database.exerciseDao()
            .getExerciseById(exerciseId)
            .map { entity -> entity?.toDomainModel() }
    }

    override suspend fun markExerciseCompleted(exerciseId: String) {
        database.exerciseDao().updateExerciseCompletion(exerciseId, true)
    }

    override suspend fun resetExerciseCompletion(exerciseId: String) {
        database.exerciseDao().updateExerciseCompletion(exerciseId, false)
    }
}
