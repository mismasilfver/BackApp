package com.backpain.exercise.data.database.dao

import androidx.room.*
import com.backpain.exercise.data.database.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    
    @Query("SELECT * FROM exercises ORDER BY title")
    fun getAllExercises(): Flow<List<ExerciseEntity>>
    
    @Query("SELECT * FROM exercises WHERE id = :id")
    fun getExerciseById(id: String): Flow<ExerciseEntity?>
    
    @Query("SELECT * FROM exercises WHERE id IN (:exerciseIds)")
    fun getExercisesBySetIds(exerciseIds: List<String>): Flow<List<ExerciseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)
    
    @Query("UPDATE exercises SET isCompleted = :isCompleted WHERE id = :exerciseId")
    suspend fun updateExerciseCompletion(exerciseId: String, isCompleted: Boolean)
    
    @Query("DELETE FROM exercises")
    suspend fun clearAllExercises()
}
